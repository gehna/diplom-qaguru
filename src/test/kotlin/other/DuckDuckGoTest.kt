package other

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import frontend.helpers.other.DuckDuckGoSearchPage
import io.kotest.matchers.string.shouldContain
import org.example.frontend.helpers.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class DuckDuckGoTest : BaseUiTest() {

    init {
        Configuration.baseUrl = "https://duckduckgo.com"
        Configuration.pageLoadStrategy = "normal"
        Configuration.reopenBrowserOnFail = true
        Configuration.timeout = 10_000
    }

    @Test
    @DisplayName("Открыть DuckDuckGo и найти Selenide")
    @Tags(Tag("frontend"), Tag("regress"), Tag("other"))
    fun openDuckDuckGoAndFindSelenide() {
        Selenide.open("/")
        Selenide.title() shouldContain "DuckDuckGo"

        val href = DuckDuckGoSearchPage()
            .search("Selenide")
            .searchResultCheck()

        href shouldContain "selenide.org"
    }
}