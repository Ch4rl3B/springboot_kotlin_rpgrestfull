package com.ch4rl3b.example.restpgdemo.repository

import com.ch4rl3b.example.restpgdemo.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<UserModel, String> {

}