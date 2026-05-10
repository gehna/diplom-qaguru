package org.example.kotlin.backend.controllers

import backend.api.models.auth.LoginRequest
import backend.api.models.auth.LoginResponse
import io.qameta.allure.Step
import org.example.backend.api.endpoints.Endpoints


import retrofit2.Response

class AuthController: Endpoints() {

    @Step(value = "Login with email: {email} and password: {password}")
    fun login(email: String, password: String): Response<LoginResponse> {
        return auth.postLogin(body = LoginRequest(email = email, password = password)).execute()
    }
}