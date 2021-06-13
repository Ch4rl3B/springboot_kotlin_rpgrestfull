package com.ch4rl3b.example.restpgdemo.services

import com.ch4rl3b.example.restpgdemo.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UsersService (private val usersRepository: UsersRepository){
    fun findAll() = usersRepository.findAll()
}