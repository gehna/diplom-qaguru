package org.example.backend.api.models.users.createUser

import backend.api.models.ErrorResponse

object CreateUserErrors {

    val duplicateCredentials = ErrorResponse(
        code = 400,
        reason = "Something went wrong. Please verify request."
    )
//    val emptyCredentials = ErrorResponse(
//        code = 400,
//        reason = "User details cannot be null or blank"
//    )
    val emptyCredentials = ErrorResponse(
        code = 400,
        reason = "Please enter username, email and password"
    )
    val invalidCredentials = ErrorResponse(
        code = 400,
        reason = "Something went wrong. Please verify request."
    )
}