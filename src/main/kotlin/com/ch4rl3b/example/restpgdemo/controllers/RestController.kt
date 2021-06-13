package com.ch4rl3b.example.restpgdemo.controllers

import com.ch4rl3b.example.restpgdemo.clases.AppRequest
import com.ch4rl3b.example.restpgdemo.clases.UserRequest
import com.ch4rl3b.example.restpgdemo.models.UserModel
import io.github.bucket4j.Bandwidth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import kotlin.reflect.KClass


abstract class RestfullController<T, E> {
    lateinit var bucket : Bucket
    val rpm : Long = 2400L

    init {
        val bandWith = Bandwidth.classic(rpm, Refill.greedy(rpm, Duration.ofMinutes(1)))
        bucket = Bucket4j.builder().addLimit(bandWith).build()
    }

    abstract fun fetchAll(request : HttpServletRequest) : ResponseEntity<List<T>>
    abstract fun create(@RequestBody body : E) : ResponseEntity<T>
}