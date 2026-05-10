package org.example.backend.api.models.users.createUser

import kotlin.random.Random

data class CreateUserRequest (
    var username: String,
    var password: String,
    var email: String
)

fun defaultUser() = CreateUserRequest(
    username = "user_${Random.nextInt(10000)}",
    password = "random",
    email = "${Random.nextInt(10000)}@autotest.com"
)