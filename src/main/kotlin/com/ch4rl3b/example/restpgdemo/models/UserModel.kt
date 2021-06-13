package com.ch4rl3b.example.restpgdemo.models

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserModel (
        @Id
        var id: String?,
        @Column(unique = true, nullable = false)
        var username: String?,
        @Column(nullable = false)
        var password: String?,
        @Column(unique = true, nullable = true)
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