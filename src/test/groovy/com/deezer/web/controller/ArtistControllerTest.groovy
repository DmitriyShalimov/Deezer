package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.service.ArtistService
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
class ArtistControllerTest {
    @InjectMocks
    ArtistController artistController

    @Mock
    ArtistService artistService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(artistController).build()
    }
    @Test
    void getArtistsByMask() {
        mockMvc.perform(get("/artist/search/{mask}", "art"))
                .andExpect(status().isOk()).andReturn()
    }

    @Test
    void getAllArtists() {
        mockMvc.perform(get("/artists"))
                .andExpect(status().isOk())
    }
}
