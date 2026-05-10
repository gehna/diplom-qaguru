package org.example.frontend.components

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import frontend.components.popup.CartPopup
import io.qameta.allure.Step
import org.example.frontend.helpers.Extenstions.Companion.shouldBeVisible
import org.example.frontend.helpers.Wrappers.Companion.byDataTestGroup

class HeaderComponent {
    private val linksHeader: ElementsCollection get() = elements(byDataTestGroup("nav-link"))
    private val headerUserPic get() = element((".avatar"))

    @Step("Нажать на ссылку в шапке: {name}")
    fun clickLink(name: String): HeaderComponent {
        linksHeader.first {  it.text().contains(name) }.click()
        return this
    }

    @Step("Получить списко ссылок в шапке")
    fun getLinks(): List<String>{
        return linksHeader.map { it.text }
    }

    @Step("Checks if avatar is present on the header after successful logging in")
    fun checkUserPic(): Boolean {
        return headerUserPic.shouldBeVisible()
    }

    @Step("Get cart popup")
    fun navigateCartPopup(): CartPopup {
        return CartPopup()
    }
}
