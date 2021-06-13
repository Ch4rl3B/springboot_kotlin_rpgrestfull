package com.ch4rl3b.example.restpgdemo.controllers

import com.ch4rl3b.example.restpgdemo.models.UserModel
import com.ch4rl3b.example.restpgdemo.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@CrossOrigin
@RequestMapping("/api/users")
class UsersController(private val usersService: UsersService) : RestfullController<UserModel>() {

    @GetMapping()
    override fun fetchAll(request: HttpServletRequest): ResponseEntity<List<UserModel>>{
        if(bucket.tryConsume(1L)){
            return ResponseEntity.ok(usersService.findAll())
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
        }
    }
}