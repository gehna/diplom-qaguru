package org.example.backend.api.endpoints

import backend.api.models.auth.LoginRequest
import backend.api.models.auth.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthEndpoints {

    @POST("auth/login")
    fun postLogin(@Body body: LoginRequest) : Call<LoginResponse>
}