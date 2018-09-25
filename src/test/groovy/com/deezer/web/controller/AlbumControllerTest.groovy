package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.Album
import com.deezer.service.AlbumService
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
class AlbumControllerTest {
    @InjectMocks
    AlbumController albumController

    @Mock
    AlbumService albumService

    private MockMvc mockMvc
    private Album album

    @Before
    void setup() {
        album = new Album(title: 'album')
        List<Album> albums = new ArrayList<>()
        albums.add(album)
        Mockito.when(albumService.getAlbumsByArtistId(1)).thenReturn(albums)
        Mockito.when(albumService.getAlbumsByMask(Mockito.anyString())).thenReturn(albums)
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(albumController).build()
    }

    @Test
    void getAlbumsByArtist() {
        def result = mockMvc.perform(get("/album/artist/{id}", 1))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(album.title))
    }

    @Test
    void getAlbumsByMask() {
        def result = mockMvc.perform(get("/album/search/{mask}", "bum"))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(album.title))
    }
}
