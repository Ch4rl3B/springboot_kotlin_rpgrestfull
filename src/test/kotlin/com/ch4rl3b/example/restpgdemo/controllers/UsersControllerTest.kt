package com.ch4rl3b.example.restpgdemo.controllers

import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.services.UsersService
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import kotlin.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
internal class UsersControllerTest{
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var usersService: UsersService


    /*
    * Here will test the getting of all objects
    * */
    @Test
    @DisplayName("Fetch all users")
    fun testFetchAllUsers(){
        val users : List<UserModel> = listOf<UserModel>(
                UserModel.registerUser("test1", "passwordtest1"),
                UserModel.registerAdmin("admin", "adminpassword")
        )
        given(usersService.findAll()).willReturn(users)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize<List<UserModel>>(2)))
    }

}