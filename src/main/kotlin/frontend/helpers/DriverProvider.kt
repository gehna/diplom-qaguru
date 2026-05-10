package org.example.frontend.helpers

import com.codeborne.selenide.WebDriverProvider
import org.openqa.selenium.Capabilities
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI

open class DriverProvider : WebDriverProvider {
    private val BROWSER_NAME = System.getProperty("browser", "chrome")

    override fun createDriver(capabilities: Capabilities): RemoteWebDriver {
        return when (BROWSER_NAME) {
            "chrome" -> {
                ChromeOptions().apply {
                    setCapability("browserVersion", "128")
                    setCapability(
                        "selenoid:options",
                        mapOf(
                            "headless" to false,
                            "enableVNC" to true,
                            "acceptInsecureCerts" to true,
                            "screenResolution" to "1920x1080"
                        )
                    )
                    addArguments("--disable-gpu")
                    addArguments("window-size=1920,1080")
                }
            }

            else -> throw Error("Browser is not defined")
        }
            .run { RemoteWebDriver(URI("https://user1:1234@selenoid.autotests.cloud/wd/hub").toURL(), this) }
            .apply { this.fileDetector = LocalFileDetector() }
    }
}

//https://user1:1234@selenoid.autotests.cloud/wd/hub