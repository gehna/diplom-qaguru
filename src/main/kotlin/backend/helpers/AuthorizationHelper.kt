package org.example.backend.helpers

import backend.api.models.auth.defaultAdmin
import backend.controllers.Controllers
import io.qameta.allure.Step
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.extention.Extensions.Companion.toBearer
import org.example.backend.api.models.users.createUser.defaultUser


class AuthorizationHelper: Controllers() {

    @Step("Get authorization token")
    fun getToken(email: String, password: String): String {
        return auth.login(email, password).getAsObject().accessToken.toBearer()
    }

    @Step("Get new user token")
    fun getNewToken(): String {
        val userRequest = defaultUser()
        users.createUser(userRequest)
        return auth.login(email = userRequest.email, password = userRequest.password).getAsObject().accessToken.toBearer()
    }

    @Step("Get admin token")
    fun getAdminToken(): String {
        return auth.login(email = defaultAdmin.email, password = defaultAdmin.password).getAsObject().accessToken.toBearer()
    }
}