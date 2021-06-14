package com.ch4rl3b.example.restpgdemo.services

import com.ch4rl3b.example.restpgdemo.clases.UserRequest
import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.repository.UsersRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException


@SpringBootTest()
internal class UsersServiceTest {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @BeforeEach
    fun setUp() {
        usersRepository.saveAndFlush(UserModel.registerUser("test1", "password"))
        usersRepository.saveAndFlush(UserModel.registerAdmin("admin", "password"))
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


    @Test
    fun testRegisterAdmin(){
        val userRequest = UserRequest("test", "password")
        val usersService = UsersService(usersRepository)
        val saved = usersService.registerAdmin(userRequest)
        val response = usersRepository.findById(saved.id!!)

        assert(response.isPresent())
        assertEquals(response.get().username,userRequest.username)
        assertEquals(response.get().role,0)
    }

    @Test
    fun testRegisterAdminWithUsernameTaken(){
        val userRequest = UserRequest("test1", "password")
        val usersService = UsersService(usersRepository)
        try {
            usersService.registerAdmin(userRequest)
            println("Gone bad way.. it supossed to throw Exception")
            assert(false)
        } catch (ex : Exception){
            println("Gone good way... it returned an Exception")
            println(ex.message)
            assert(ex is DataIntegrityViolationException)
        }
    }

    @Test
    fun testRegisterUser(){
        val userRequest = UserRequest("test", "password")
        val usersService = UsersService(usersRepository)
        val saved = usersService.registerUser(userRequest)
        val response = usersRepository.findById(saved.id!!)

        assert(response.isPresent())
        assertEquals(response.get().username,userRequest.username)
        assertEquals(response.get().role,1)
    }

    @Test
    fun testRegisterUserWithUsernameTaken(){
        val userRequest = UserRequest("test1", "password")
        val usersService = UsersService(usersRepository)
        try {
            usersService.registerUser(userRequest)
            println("Gone bad way.. it supossed to throw Exception")
            assert(false)
        } catch (ex : Exception){
            println("Gone good way... it returned an Exception")
            println(ex.message)
            assert(ex is DataIntegrityViolationException)
        }
    }
}