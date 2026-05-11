package frontend.helpers.other

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import io.qameta.allure.Step
import org.openqa.selenium.By

class DuckDuckGoSearchPage {

    private val searchInput get() = element(By.name("q"))
    private val resultLinks get() = elements("a[href^='https://selenide.org']")

    @Step("Search for: {query}")
    fun search(query: String): DuckDuckGoSearchPage {
        searchInput.shouldBe(visible)
        searchInput.value = query
        searchInput.pressEnter()
        return this
    }

    @Step("Get first visible result href")
    fun searchResultCheck(): String {
        val link = resultLinks.findBy(visible).shouldBe(visible)
        return link.getAttribute("href") ?: ""
    }
}