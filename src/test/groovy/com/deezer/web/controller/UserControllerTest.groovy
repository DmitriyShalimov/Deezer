package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.User
import com.deezer.service.security.SecurityService
import com.deezer.service.security.entity.UserToken
import com.deezer.web.controller.view.UserController
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
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import static org.junit.Assert.*

@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    @InjectMocks
    UserController userController

    @Mock
    SecurityService securityService

    private MockMvc mockMvc

    @Before
    void setup() {
        User user = new User(login: 'login')
        def token = new UserToken('1', user, LocalDateTime.now())
        Mockito.when(securityService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(token)
        Mockito.when(securityService.register((User) Mockito.notNull())).thenReturn(token)
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }


    @Test
    void doLogin() {
        RequestBuilder requestBuilder = post("/login")
                .param("login", 'login')
                .param("password", 'password')
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn()
    }


    @Test
    void register() {
        RequestBuilder requestBuilder = post("/registration")
                .param("login", 'login')
                .param("password", 'password')
        def result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn()
        def response = result.getResponse().contentAsString
        assertTrue(response.contains("\"uuid\":\"1\""));
    }
}
