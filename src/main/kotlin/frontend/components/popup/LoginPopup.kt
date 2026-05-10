package frontend.components.popup

import com.codeborne.selenide.Selectors.shadowCss
import com.codeborne.selenide.Selenide.element
import io.qameta.allure.Step
import org.example.frontend.helpers.Wrappers.Companion.byDataTestId

class LoginPopup {
    private val btnClose get() = element(byDataTestId("login-close"))
//    private val loginWindowTitle get() = element(byDataTestId("login-title"))
    private val loginWindowTitle get() = element(".dialog-backdrop .dialog .title")
    private val emailInputLogin get() = element(byDataTestId("login-email")).find(shadowCss(".input"))
    private val passwordInputLogin get() = element(byDataTestId("login-password")).find(shadowCss(".input"))
    private val loginButtong get() = element(byDataTestId("login-submit"))
    private val errorMessageLogin get() = element(byDataTestId("login-error"))

    @Step("Press close popup window button")
    fun clickClosePopupButton(): LoginPopup {
        btnClose.click()
        return this
    }

    //@Step("Checkout popup window title is 'Login'")
    fun getLoginWindowTitle(): String {
        return loginWindowTitle.text
    }

    @Step("Check credentials input for login window popup")
    fun loginWindowInput(email: String, password: String): LoginPopup {
        emailInputLogin.value = email
        passwordInputLogin.value = password
        loginButtong.click()
        return this
    }

    @Step("Error text check for invalid credentials input")
    fun getErrorText(expectedError: String): String {
        return errorMessageLogin.text
    }
}