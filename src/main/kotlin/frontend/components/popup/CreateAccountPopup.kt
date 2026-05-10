package org.example.frontend.components.popup

import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Selectors.shadowCss
import com.codeborne.selenide.Selenide.element
import io.qameta.allure.Step
import org.example.frontend.helpers.Wrappers.Companion.byDataTestId

class CreateAccountPopup {

    private val usernameInput get() = element(byDataTestId("create-username")).find(shadowCss(".input"))
    private val emailInput get() = element(byDataTestId("create-email")).find(shadowCss(".input"))
    private val passwordInput get() = element(byDataTestId("create-password")).find(shadowCss(".input"))
    private val submitButton get() = element(byDataTestId("create-submit"))
    private val errorMessage get() = element(byDataTestId("create-error"))

    @Step("Check user login with given credentials")
    fun joinAs(username: String, email: String, password: String): CreateAccountPopup {
        usernameInput.value = username
        emailInput.value = email
        passwordInput.value = password
        submitButton.click()
        return this
    }
//    @Step("Error text check for invalid credentials input")
//    fun getErrorMessage(expectedError: String): String {
//        return errorMessage.text
//    }

    @Step("Error text check for invalid credentials input")
    fun getErrorMessage(expectedError: String): String {
        errorMessage.shouldHave(exactText(expectedError))
        return errorMessage.text
    }
}