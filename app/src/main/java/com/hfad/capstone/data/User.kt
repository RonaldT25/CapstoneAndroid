package com.hfad.capstone.data

data class User(
        val username: String,
        val email: String,
        val password: String,
        val role: String,
        val verifiedEmail: Boolean,
        val profileImage: String?,
        val tokenTokopedia: String?
)
