package com.deezer.dao.jdbc

import com.deezer.config.AppTestConfig
import com.deezer.dao.ArtistDao
import com.deezer.dao.jdbc.datasource.DBInitializer
import com.deezer.entity.Artist
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [AppTestConfig.class])
class JdbcArtistDaoITest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static final String SCHEMA_FILE_PATH = "/db/playlist-schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/playlist-data.xml"
    static DBInitializer dbInitializer = new DBInitializer()

    @Autowired
    ArtistDao artistDao

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
    void getAll() {
        def expectedArtist = {
            id: 1
            name: 'artist'
            picture: 'picture'
        } as Artist
        assertEquals(expectedArtist, artistDao.getAll())
    }

    @Test
    void getArtistsByMask() {
        def expectedArtist = {
            id: 1
            name: 'artist'
            picture: 'picture'
        } as Artist
        assertEquals(expectedArtist, artistDao.getArtistsByMask('ti'))
    }
}
