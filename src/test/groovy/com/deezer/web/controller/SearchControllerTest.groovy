package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.User
import com.deezer.service.SearchService
import com.deezer.web.security.AuthPrincipal
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
class SearchControllerTest {
    @InjectMocks
    SearchController searchController

    @Mock
    SearchService searchService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchController).build()
    }

    @Test
    void getSearchResults() {
        def user = new User(id:1)
        mockMvc.perform(get("/search").principal(new AuthPrincipal(user)))
                .andExpect(status().isOk())
    }
}
