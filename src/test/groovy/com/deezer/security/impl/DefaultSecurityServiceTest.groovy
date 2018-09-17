package com.deezer.security.impl

import com.deezer.UnitTest
import com.deezer.entity.User
import com.deezer.security.SecurityService
import com.deezer.service.UserService
import org.junit.Test
import org.junit.experimental.categories.Category

import static org.junit.Assert.*

@Category(UnitTest.class)
class DefaultSecurityServiceTest {
    @Test
    void register() {
        def user = new User(id: 1, login: 'user', password: 'pass')
        def userService = [isLoginUnique: { login -> true }, add: { userToAdd -> user }] as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        assertTrue(securityService.register(user))
    }

    @Test
    void authenticate() {
        def expectedUser = new User(login: 'zhenya', password: 'e5cb3d32c13cb96fb7ceae9841115ac6ca4794cf', salt: "501d77d5-ffa4-46a9-a862-3b4b7c661c03")
        def userService = { getByLogin -> Optional.of(expectedUser) } as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        def actualUser = securityService.authenticate('zhenya', '333')
        assertTrue(actualUser.isPresent())
    }

    @Test
    void authenticateInvalidCredentials() {
        def expectedUser = new User(login: 'zhenya', password: 'e5cb3d32c13cb96fb7ceae9841115ac6ca4794cf', salt: "501d77d5-ffa4-46a9-a862-3b4b7c661c03")
        def userService = { getByLogin -> Optional.of(expectedUser) } as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        def actualUser = securityService.authenticate('zhenya', 'wrongPass')
        assertFalse(actualUser.isPresent())
    }
}
