package com.deezer.dao.jdbc

import com.deezer.config.AppTestConfig
import com.deezer.dao.GenreDao
import com.deezer.dao.SearchDao
import com.deezer.dao.jdbc.datasource.DBInitializer
import com.deezer.entity.Album
import com.deezer.entity.Artist
import com.deezer.entity.SearchResult
import com.deezer.entity.Song
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertTrue

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [AppTestConfig.class])
class JdbcSearchDaoITest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static final String SCHEMA_FILE_PATH = "/db/playlist-schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/playlist-data.xml"
    static DBInitializer dbInitializer = new DBInitializer()

    @Autowired
    SearchDao searchDao

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
    void getSearchResults() {
        def expectedResults = [new Song(id: 1, title: 'song',
                album: new Album(id: 0, title: 'album'),
                artist: new Artist(id: 0, name: 'artist'),
                picture: 'song_picture',
                url: 'track_url'), new Album(id: 1, artist: new Artist(id: 0, name: 'artist'),
                title: 'album', picture: 'album_picture'),
                               new Artist(id: 1, name: 'artist', picture: 'picture')] as List<SearchResult>
        def actualResults = searchDao.getSearchResults(1)
        actualResults.each {
            assertTrue(expectedResults.remove(it))
        }
    }
}
