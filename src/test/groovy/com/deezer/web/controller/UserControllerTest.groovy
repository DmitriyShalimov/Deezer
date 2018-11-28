package com.deezer.web.controller

import com.deezer.UnitTest
import com.deezer.entity.User
import com.deezer.service.security.SecurityService
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
        Mockito.when(securityService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(user))
        Mockito.when(securityService.register((User) Mockito.notNull())).thenReturn(true)
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    void showLogin() {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
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
    void registration() {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration.html"))
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
        assertEquals("success", response)
    }
}
