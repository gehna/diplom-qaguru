package org.example.frontend.pages

import com.codeborne.selenide.CollectionCondition.sizeGreaterThan
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.codeborne.selenide.SelenideElement
import frontend.components.list.ProductItem
import frontend.components.list.ProductCard
import io.qameta.allure.Step
import org.example.frontend.components.HeaderComponent
import org.example.frontend.helpers.Wrappers.Companion.byDataTestGroup
import org.example.frontend.helpers.Wrappers.Companion.byDataTestId

class MainPage {
    private val txtTitle get() = element(byDataTestId("main-image-text"))
    private val listPopularProducts: ElementsCollection get() = elements(byDataTestGroup("product-card"))

    @Step("Open main page")
    fun open(): MainPage {
        Selenide.open("/")
        return this
    }

    @Step("Get main page name {string}")
    fun getTitle(): String {
        return txtTitle.text
    }

    @Step("Go to header component")
    fun navigateHeader(): HeaderComponent {
        return HeaderComponent()
    }

    @Step("Get popular products list")
    fun getPopularProducts(): List<ProductItem> {
        return ProductCard(listPopularProducts).getItems()
    }
}