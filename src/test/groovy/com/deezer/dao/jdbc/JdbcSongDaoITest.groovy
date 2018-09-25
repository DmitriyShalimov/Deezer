package com.deezer.dao.jdbc

import com.deezer.config.AppTestConfig
import com.deezer.dao.SongDao
import com.deezer.dao.jdbc.datasource.DBInitializer
import com.deezer.entity.Album
import com.deezer.entity.Artist
import com.deezer.entity.Song
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
class JdbcSongDaoITest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static final String SCHEMA_FILE_PATH = "/db/playlist-schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/playlist-data.xml"
    static DBInitializer dbInitializer = new DBInitializer()

    @Autowired
    SongDao songDao

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
    void getSong() {
        def expectedSong = {
            id: 1
            title: 'song'
            artist:
            new Artist(name: 'artist')
            album:
            new Album(title: 'album')
            url: 'tract_url'
            picture: 'song_picture'
        } as Song
        assertEquals(expectedSong, songDao.getSong(1))
    }

    @Test
    void getSongsByGenre() {
        def expectedSong = {
            id: 1
            title: 'song'
            artist:
            new Artist(name: 'artist')
            album:
            new Album(title: 'album')
            url: 'tract_url'
            picture: 'song_picture'
        } as Song
        assertEquals(expectedSong, songDao.getSongsByGenre(1))
    }

    @Test
    void getSongsByArtist() {
        def expectedSong = {
            id: 1
            title: 'song'
            artist:
            new Artist(name: 'artist')
            album:
            new Album(title: 'album')
            url: 'tract_url'
            picture: 'song_picture'
        } as Song
        assertEquals(expectedSong, songDao.getSongsByArtist(1))
    }

    @Test
    void getSongsByAlbum() {
        def expectedSong = {
            id: 1
            title: 'song'
            artist:
            new Artist(name: 'artist')
            album:
            new Album(title: 'album')
            url: 'tract_url'
            picture: 'song_picture'
        } as Song
        assertEquals(expectedSong, songDao.getSongsByAlbum(1))
    }

    @Test
    void gGetSongsByMask() {
        def expectedSong = {
            id: 1
            title: 'song'
            artist:
            new Artist(name: 'artist')
            album:
            new Album(title: 'album')
            url: 'tract_url'
            picture: 'song_picture'
        } as Song
        assertEquals(expectedSong, songDao.getSongsByMask("on"))
    }
}
