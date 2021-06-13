package com.ch4rl3b.example.restpgdemo.clases
import com.ch4rl3b.example.restpgdemo.models.UserModel

class UserRequest (val username : String, val password : String) : AppRequest<UserModel>
