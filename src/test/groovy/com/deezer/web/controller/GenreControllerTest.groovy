package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.Genre
import com.deezer.service.GenreService
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
class GenreControllerTest {
    @InjectMocks
    GenreController genreController

    @Mock
    GenreService genreService

    private MockMvc mockMvc
    private Genre genre

    @Before
    void setup() {
        genre = new Genre(title: 'genre')
        List<Genre> genres = new ArrayList<>()
        genres.add(genre)
        Mockito.when(genreService.getAll()).thenReturn(genres)
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(genreController).build()
    }

    @Test
    void getAllGenres() {
        def result = mockMvc.perform(get("/genres"))
                .andExpect(status().isOk()).andReturn()
        def response = result.getResponse().getContentAsString()
        assertTrue(response.contains(genre.title))
    }
}
