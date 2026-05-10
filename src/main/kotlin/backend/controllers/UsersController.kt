package org.example.backend.controllers

import io.qameta.allure.Step
import okhttp3.ResponseBody
import org.example.backend.api.endpoints.Endpoints
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.models.users.createUser.CreateUserRequest
import org.example.backend.api.models.users.createUser.CreateUserResponse
import org.example.backend.api.models.users.updateUser.UpdateRequest
import org.example.backend.helpers.AuthorizationHelper
import org.example.backend.helpers.GarbageCollector
import retrofit2.Response

class UsersController: Endpoints() {

    private val authHelper = AuthorizationHelper()

    @Step("Get all users")
    fun getAllUsers(token: String = authHelper.getAdminToken(), offset: Int = 0, limit: Int = 50): Response<List<CreateUserResponse>> {
        return users.getUsers(token,offset,limit).execute()
    }

    @Step("Create new user")
    fun createUser(body: CreateUserRequest): Response<CreateUserResponse> {
        return users.createUser(body).execute()
            .also { if (it.isSuccessful) GarbageCollector.user.add(it.getAsObject().id) }
    }

    @Step("Get user with ID: {id}")
    fun getUserById(token: String = authHelper.getAdminToken(), id: Int): Response<CreateUserResponse> {
        return users.getUserById(token, id).execute()
    }

    @Step("Update users with ID: {id}")
    fun updateUserById(token: String = authHelper.getAdminToken(), id: Int, body: UpdateRequest): Response<CreateUserResponse> {
        return users.putUserById(token, id, body).execute()
    }

    @Step("Delete user with ID: {id}")
    fun deleteUserById(token: String = authHelper.getAdminToken(), id: Int): Response<ResponseBody> {
        return users.deleteUserById(token, id).execute()
    }
}