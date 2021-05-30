package com.hfad.capstone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
        @PrimaryKey val id: Int,
        val username: String,
        val email: String,
        val password: String,
        val role: String,
        val verifiedEmail: Boolean,
        val profileImage: String?,
        val tokenTokopedia: String?
)
