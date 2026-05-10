package org.example.backend.api.models.users.createUser

data class CreateUserResponse(
    val createdAt: Long,
    val email: String,
    val id: Int,
    val phoneNumber: String,
    val username: String
)