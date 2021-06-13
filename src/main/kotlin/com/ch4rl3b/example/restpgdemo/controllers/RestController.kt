package com.ch4rl3b.example.restpgdemo.controllers

import io.github.bucket4j.Bandwidth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import org.springframework.http.ResponseEntity
import java.time.Duration
import javax.servlet.http.HttpServletRequest


abstract class RestfullController<T> {
    lateinit var bucket : Bucket
    val rpm : Long = 2400L

    init {
        val bandWith = Bandwidth.classic(rpm, Refill.greedy(rpm, Duration.ofMinutes(1)))
        bucket = Bucket4j.builder().addLimit(bandWith).build()
    }

    abstract fun fetchAll(request : HttpServletRequest) : ResponseEntity<List<T>>
}