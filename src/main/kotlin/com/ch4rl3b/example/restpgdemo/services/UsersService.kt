package com.ch4rl3b.example.restpgdemo.services

import com.ch4rl3b.example.restpgdemo.clases.UserRequest
import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class UsersService (val usersRepository: UsersRepository){
    fun findAll() = usersRepository.findAll(Sort.by(Sort.Direction.DESC, "username"))
    fun registerAdmin(body: UserRequest) = usersRepository.saveAndFlush(UserModel.registerAdmin(body.username, body.password))
    fun registerUser(body: UserRequest) = usersRepository.saveAndFlush(UserModel.registerUser(body.username, body.password))
}