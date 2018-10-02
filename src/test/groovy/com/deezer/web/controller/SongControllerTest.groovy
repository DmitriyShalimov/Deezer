package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.User
import com.deezer.service.SongService
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
class SongControllerTest {
    @InjectMocks
    SongController songController

    @Mock
    SongService songService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(songController).build()
    }

    @Test
    void getSong() {
        def user = new User(id:1)
        mockMvc.perform(get("/song/{id}", 1)
                .sessionAttr('loggedUser', user))
                .andExpect(status().isOk())
    }

    @Test
    void getSongsByMask() {
        def user = new User(id:1)
        mockMvc.perform(get("/song/search/{mask}", "song")
                .sessionAttr('loggedUser', user))
                .andExpect(status().isOk())
    }
}
