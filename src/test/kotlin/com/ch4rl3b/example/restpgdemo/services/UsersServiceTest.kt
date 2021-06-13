package com.ch4rl3b.example.restpgdemo.services

import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.repository.UsersRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
internal class UsersServiceTest {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @BeforeEach
    fun setUp() {
        usersRepository.save(UserModel.registerUser("test1", "passwordtest1"))
        usersRepository.save(UserModel.registerAdmin("admin", "adminpassword"))
        usersRepository.flush()
    }

    @AfterEach
    fun tearDown() {
        usersRepository.deleteAll()
    }

    @Test
    fun testFindAll() {
      val usersService : UsersService = UsersService(usersRepository)
      val list = usersService.findAll()
      val last = list.last()

      assertEquals(2, list.size)
      assertEquals("admin", last.username)
      assertEquals(0, last.role)
    }

}