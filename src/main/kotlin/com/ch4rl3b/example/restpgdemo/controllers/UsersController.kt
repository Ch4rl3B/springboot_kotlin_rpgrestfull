package com.ch4rl3b.example.restpgdemo.controllers

import com.ch4rl3b.example.restpgdemo.clases.AppRequest
import com.ch4rl3b.example.restpgdemo.clases.UserRequest
import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import kotlin.reflect.KClass

@RestController
@CrossOrigin
@RequestMapping("/api/users")
class UsersController(private val usersService: UsersService) : RestfullController<UserModel, UserRequest>() {

    @GetMapping()
    override fun fetchAll(request: HttpServletRequest): ResponseEntity<List<UserModel>>{
        if(bucket.tryConsume(1L)){
            return ResponseEntity.ok(usersService.findAll())
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
        }
    }

    @PostMapping
    override fun create(@RequestBody body: UserRequest): ResponseEntity<UserModel> {
        if(bucket.tryConsume(1L)){
            return ResponseEntity<UserModel>(usersService.registerUser(body), HttpStatus.CREATED)
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
        }
    }

    @PostMapping("/admin")
    fun createAdmin(@RequestBody body: UserRequest): ResponseEntity<UserModel> {
        if(bucket.tryConsume(1L)){
            return ResponseEntity<UserModel>(usersService.registerAdmin(body), HttpStatus.CREATED)
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
        }
    }

}