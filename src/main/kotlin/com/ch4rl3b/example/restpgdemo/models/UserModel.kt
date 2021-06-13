package com.ch4rl3b.example.restpgdemo.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserModel (
        @Id
        var id: String?,
        var username: String?,
        var password: String?,
        var token: String?,
        var role: Int? // 0: admin, 1: user
){
        companion object {
                fun registerUser(username: String?, password: String?) : UserModel {
                    return UserModel(UUID.randomUUID().toString(), username, password, null, 1);
                }

                fun registerAdmin(username: String?, password: String?) : UserModel {
                return UserModel(UUID.randomUUID().toString(), username, password, null, 0);
            }
        }

}