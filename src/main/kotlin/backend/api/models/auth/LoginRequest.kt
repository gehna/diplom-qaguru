package backend.api.models.auth

data class LoginRequest(
    var email: String,
    var password: String
)

val defaultAdmin = LoginRequest(
    email = "admin@autotest.com",
    password = "admin"
)