package frontend.pages

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selectors.shadowCss
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.codeborne.selenide.SelenideElement
import frontend.components.list.OrderItem
import frontend.components.list.OrderItems
import io.qameta.allure.Step
import org.example.frontend.helpers.Wrappers.Companion.byDataTestGroup

class OrdersPage {
    private val placeholderText: SelenideElement get() = element("[placeholder='Order ID']")

    private val orderIdInput get() = element("[placeholder='Order ID']").find(shadowCss("input"))

    private val orderItems: ElementsCollection get() = elements(byDataTestGroup("order-card"))

    private val orderErrorMessage: SelenideElement get() = element(".order-error")

    @Step("Open orders page")
    fun open(): OrdersPage {
        Selenide.open("/orders")
        return this
    }

    @Step("Check if placeholder 'Order ID'-text is present in input field")
    fun placeHolderName(string: String): String {
        return placeholderText.text
    }

    @Step("Enter order ID: {id}")
    fun enterOrderId(id: Int): OrdersPage {
        orderIdInput.value = id.toString()
        orderIdInput.pressEnter()
        return this
    }

    @Step("Get products object list")
    fun getOrderItems(): List<OrderItem> {
        orderItems.shouldHave(CollectionCondition.sizeGreaterThan(0)) //добавил еще одно ожидание из-за RC
        return OrderItems().getItems()
    }

//    @Step("Get error message")
//    fun getOrderErrorMessage(expectedMessage: String): String {
//        return orderErrorMessage.text
//    }

    @Step("Get error message")
    fun getOrderErrorMessage(expectedMessage: String): String {
        orderErrorMessage.shouldHave(exactText(expectedMessage))
        return orderErrorMessage.text
    }
}