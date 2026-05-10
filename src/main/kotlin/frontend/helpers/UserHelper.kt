package org.example.kotlin.frontend.helpers

import backend.controllers.Controllers
import io.qameta.allure.Step
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.helpers.GarbageCollector
import org.example.frontend.components.popup.CreateAccountPopup
import org.example.frontend.pages.MainPage

class UserHelper : Controllers() {

    @Step("Сгенерировать тестовые данные пользователя")
    fun generateUser(): UserCredentials {
        val ts = System.currentTimeMillis()
        return UserCredentials(
            username = "user_$ts",
            email = "user_$ts@test.ru",
            password = "Password123!"
        )
    }

    @Step("Создать пользователя через UI: {creds.email}")
    fun createUserViaUi(creds: UserCredentials): UserCredentials {
        MainPage()
            .navigateHeader()
            .clickLink("Join")
        CreateAccountPopup()
            .joinAs(creds.username, creds.email, creds.password)
        addUserToGC(creds.email)
        return creds
    }

    @Step("Добвить юзера в GarbageCollector: {creds.email}")
    fun addUserToGC(email: String) {
        users.getAllUsers()
            .getAsObject()
            .firstOrNull { it.email == email }
            ?.let {
                GarbageCollector.user.add(it.id)
                println("Юзер добавлен в Garbage Collector: ${it.id}, email: ${it.email}")
            }
    }
}

data class UserCredentials(val username: String, val email: String, val password: String)