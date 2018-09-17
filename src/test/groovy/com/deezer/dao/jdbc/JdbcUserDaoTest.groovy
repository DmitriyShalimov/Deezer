package com.deezer.dao.jdbc

import com.deezer.UnitTest
import com.deezer.config.AppTestConfig
import com.deezer.dao.UserDao
import com.deezer.dao.jdbc.datasource.DBInitializer
import com.deezer.entity.User
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [AppTestConfig.class])
class JdbcUserDaoITest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static final String SCHEMA_FILE_PATH = "/db/schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/user-data.xml"
    static DBInitializer dbInitializer = new DBInitializer()

    @Autowired
    UserDao userDao

    @BeforeClass
    static void setUp() {
        dbInitializer.createSchema(PROPERTY_FILE_PATH, SCHEMA_FILE_PATH)
    }

    @Before
    void importDataSet() throws Exception {
        IDataSet dataSet = dbInitializer.readDataSet(DATASET_FILE_PATH)
        dbInitializer.cleanlyInsert(dataSet)
    }

    @Test
    void add() {
        def user = new User(id: 2, login: 'user', password: 'pass', salt: 'salt')
        userDao.add(user)
        def insertedUser = userDao.getByLogin('user')
        assertTrue(insertedUser.isPresent())
        assertEquals(user, insertedUser.get())
    }

    @Test
    void getByLogin() {
        def expectedUser = new User(id: 1, login: 'zhenya', password: '1234', salt: 'salt')
        def actualUser = userDao.getByLogin('zhenya')
        assertTrue(actualUser.isPresent())
        assertEquals(expectedUser, actualUser.get())
    }

    @Test
    void getByInvalidLogin(){
        def user = userDao.getByLogin('invalidUser')
        assertFalse(user.isPresent())
    }
}
