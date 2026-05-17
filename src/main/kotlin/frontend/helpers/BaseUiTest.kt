package org.example.frontend.helpers

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import org.example.kotlin.general.Config
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.remote.DesiredCapabilities

open class BaseUiTest {
    init {
        val props = Config.getProps
        Configuration.baseUrl = props.frontendUrl
        Configuration.timeout = 15_000
        Configuration.pageLoadStrategy = "normal"
        Configuration.reopenBrowserOnFail = true
        Configuration.browser = props.browserName

        if (props.remoteEnabled && props.moonHost.isNotBlank()) {
            Configuration.remote = props.moonHost
            Configuration.browserVersion = props.browserVersion
            val selenoidOptions = mapOf<String, Any>(
                "enableVNC" to true,
                "enableVideo" to false,
                "sessionTimeout" to "5m",
                "name" to "ui-test"
            )
            val capabilities = DesiredCapabilities()
            capabilities.setCapability("selenoid:options", selenoidOptions)
            Configuration.browserCapabilities = capabilities
        }

        println("[BaseUiTest] frontendUrl=${props.frontendUrl}")
        println("[BaseUiTest] baseUrl=${Configuration.baseUrl}")
        println("[BaseUiTest] remote=${Configuration.remote}")
        println("[BaseUiTest] browser=${Configuration.browser} version=${Configuration.browserVersion}")
    }

    @BeforeEach
    fun openBrowser() {
        Selenide.open("/")

        println("[BaseUiTest] title=${Selenide.title()}")
    }

    @AfterEach
    fun clearBrowser() {
        Selenide.clearBrowserCookies()
        Selenide.clearBrowserLocalStorage()
    }
}