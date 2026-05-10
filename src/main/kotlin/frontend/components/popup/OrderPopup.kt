package frontend.components.popup

import com.codeborne.selenide.Selenide.element
import io.qameta.allure.Step
import org.example.frontend.helpers.Wrappers.Companion.byDataTestId


class OrderPopup {
    private val orderPopupTitle get() = element(byDataTestId("order-popup-title"))
    private val orderPopupClose get() = element(byDataTestId("order-popup-close"))
    private val orderPopupId get() = element(byDataTestId("order-popup-id"))
    private val orderPopupStatus get() = element(byDataTestId("order-popup-status"))

    @Step("Order popup window title")
    fun orderPopupTitle(): String {
        return orderPopupTitle.text
    }

    @Step("Order popup close button")
    fun orderPopupCloseBtn() {
        return orderPopupClose.click()
    }

    @Step("Order ID")
    fun orderPopupWindowId(): String {
        return orderPopupId.text
    }

    @Step("Order status")
    fun orderPopupWindowStatus(): String {
        return orderPopupStatus.text
    }

    @Step("Get order ID from popup")
    fun getOrderId(): Int {
        return orderPopupId.text
            .replace("Order ID:", "")
            .trim()
            .toInt()
    }
}