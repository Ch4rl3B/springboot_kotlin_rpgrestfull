package com.ch4rl3b.example.restpgdemo.controllers

import com.ch4rl3b.example.restpgdemo.clases.UserRequest
import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.repository.UsersRepository
import com.ch4rl3b.example.restpgdemo.services.UsersService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@WebMvcTest
internal class UsersControllerTest{
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var usersServiceMock: UsersService


    /*
    * Client needs an endpoint for getting all users
    * */
    @Test
    @DisplayName("Fetch all users")
    fun testFetchAllUsers(){
        val users : List<UserModel> = listOf<UserModel>(
                UserModel.registerUser("test1", "passwordtest1"),
                UserModel.registerAdmin("admin", "adminpassword")
        )
        given(usersServiceMock.findAll()).willReturn(users)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize<List<UserModel>>(2)))
    }


    /*
    * Client needs to protect the api against DDoS Attacks
    * */
    @Test
    @DisplayName("Fill the Bucket will return Exception")
    fun testDDoSAttack(){
        val usersController = UsersController(usersServiceMock);

        for(i in List(2401, { it })) {
            assertTrue(usersController.bucket.tryConsume(1))
        }
        assertFalse(usersController.bucket.tryConsume(1))
    }

    private inline fun <reified T> any(type: Class<T>): T = Mockito.any(type)

    /**
     * Client needs an endpoint for registrer new admin user
     */
    @Test
    @DisplayName("Register a new admin")
    fun testRegisterNewAdmin(){
        val userRequest = UserRequest("admin", "qwerty")
       `when`(usersServiceMock.registerAdmin(any(UserRequest::class.java))).thenReturn(UserModel.registerAdmin(userRequest.username, userRequest.password))

        val objectMapper = ObjectMapper()
        val jsonBody = objectMapper.writeValueAsString(userRequest)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value(0))
                .andExpect(jsonPath("$.username").value(userRequest.username))
                .andExpect(jsonPath("$.id").exists())
    }

    /**
     * Client needs an endpoint for registering a normal user that will be a player
     */
    @Test
    @DisplayName("Register a new player")
    fun testRegisterNewPlayer(){
        val userRequest = UserRequest("user", "qwerty")
        `when`(usersServiceMock.registerUser(any(UserRequest::class.java))).thenReturn(UserModel.registerUser(userRequest.username, userRequest.password))

        val objectMapper = ObjectMapper()
        val jsonBody = objectMapper.writeValueAsString(userRequest)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value(1))
                .andExpect(jsonPath("$.username").value(userRequest.username))
                .andExpect(jsonPath("$.id").exists())
    }

}