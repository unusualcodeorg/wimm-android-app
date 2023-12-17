package com.whereismymotivation.data.model

data class AppUser(

    val userId: String,

    val userName: String?,

    val userEmail: String?,

    val profilePicUrl: String? = null,

    val accessToken: String,

    val refreshToken: String,

    val userRoles: List<Role>
)