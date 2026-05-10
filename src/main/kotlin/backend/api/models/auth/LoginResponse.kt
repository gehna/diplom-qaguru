package backend.api.models.auth

data class LoginResponse(
    var accessToken: String,
    var createdAt: Long,
    var expireInMs: Long,
    var id: Int,
    var refreshToken: String
)
