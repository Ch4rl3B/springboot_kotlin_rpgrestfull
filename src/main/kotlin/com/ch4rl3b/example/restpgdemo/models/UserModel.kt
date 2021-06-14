package com.ch4rl3b.example.restpgdemo.models

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@NoArgsConstructor
data class UserModel (
        @Id
        var id: String? = null,
        @Column(unique = true, nullable = false)
        var username: String? = null,
        @Column(nullable = false)
        var password: String? = null,
        @Column(unique = true, nullable = true)
        var token: String? = null,
        var role: Int?  = null // 0: admin, 1: user
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