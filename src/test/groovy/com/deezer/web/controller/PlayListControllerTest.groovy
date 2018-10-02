package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.Song
import com.deezer.entity.User
import com.deezer.service.SongService
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.junit.Assert.assertTrue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
class PlayListControllerTest {
    @InjectMocks
    PlayListController playListController

    @Mock
    SongService songService

    private MockMvc mockMvc
    private Song song

    @Before
    void setup() {
        song = new Song(title: 'album')
        List<Song> songs = new ArrayList<>()
        songs.add(song)
        Mockito.when(songService.getSongsByAlbum(Mockito.anyInt(), Mockito.anyInt())).thenReturn(songs)
        Mockito.when(songService.getSongsByGenre(Mockito.anyInt(), Mockito.anyInt())).thenReturn(songs)
        Mockito.when(songService.getSongsByArtist(Mockito.anyInt(), Mockito.anyInt())).thenReturn(songs)
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(playListController).build()
    }

    @Test
    void getSongsByAlbum() {
        def user = new User(id:1)
        def result = mockMvc.perform(get("/album/{id}/songs", 1)
                .sessionAttr('loggedUser', user))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(song.title))
    }

    @Test
    void getSongsByGenre() {
        def user = new User(id:1)
        def result = mockMvc.perform(get("/genre/{id}/songs", 1)
                .sessionAttr('loggedUser', user))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(song.title))
    }

    @Test
    void getSongsByArtist() {
        def user = new User(id:1)
        def result = mockMvc.perform(get("/artist/{id}/songs", 1)
                .sessionAttr('loggedUser', user))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(song.title))
    }
}
