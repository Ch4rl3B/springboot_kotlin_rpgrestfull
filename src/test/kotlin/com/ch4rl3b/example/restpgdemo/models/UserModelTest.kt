package com.ch4rl3b.example.restpgdemo.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

internal class UserModelTest{

    @Test
    @DisplayName("Register a user as normal user")
    fun registerAnUser(){
        val newUser = UserModel.registerUser("test1", "passwordtest1")
        assertThat(newUser.id).isNotBlank()
        assertThat(newUser.username).isEqualTo("test1")
        assertThat(newUser.password).isEqualTo("passwordtest1")
        assertThat(newUser.role).isEqualTo(1)
    }

    @Test
    @DisplayName("Register a user as administrator")
    fun registerAnAdmin(){
        val newUser = UserModel.registerAdmin("test1", "passwordtest1")
        assertThat(newUser.id).isNotBlank()
        assertThat(newUser.username).isEqualTo("test1")
        assertThat(newUser.password).isEqualTo("passwordtest1")
        assertThat(newUser.role).isEqualTo(0)
    }
}