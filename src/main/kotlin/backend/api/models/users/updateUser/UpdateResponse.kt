package org.example.backend.api.models.users.updateUser

data class UpdateResponse(
    val id: Int,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val createdAt: Long
)