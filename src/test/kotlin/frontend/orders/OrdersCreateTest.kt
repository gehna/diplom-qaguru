package frontend.orders

import frontend.components.popup.CartPopup
import frontend.helpers.OrderHelperFrontend
import frontend.pages.OrdersPage
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.example.frontend.helpers.BaseUiTest
import org.example.frontend.pages.MainPage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class OrdersCreateTest : BaseUiTest() {

    private val orderHelper = OrderHelperFrontend()
    private val mainPage = MainPage()
    private val cartPopup = CartPopup()
    private val ordersPage = OrdersPage()

    @Test
    @DisplayName("UI: заказ, созданный через корзину, имеет валидный order id")
    @Tags(Tag("frontend"), Tag("regress"), Tag("orders"), Tag("smoke"))
    fun shouldCreateOrderFromCartCheckout() {
        val firstProduct = mainPage.getPopularProducts().first()
        firstProduct.btnIncrement.click()

        mainPage.navigateHeader().clickLink("Cart")
        val orderPopup = cartPopup.checkoutButtonClick()

        val orderId = orderPopup.getOrderId()
        orderId shouldBeGreaterThan 0
        orderHelper.addOrderToGC(orderId)

        orderPopup.orderPopupCloseBtn()
    }

    @Test
    @DisplayName("UI: страница заказов показывает ошибку для несуществующего order id")
    @Tags(Tag("frontend"), Tag("regress"), Tag("orders"), Tag("negative"))
    fun shouldShowErrorForNonExistentOrderId() {
        val nonExistentId = 999_999_999
        val expectedError = "Order with id:$nonExistentId not found"

        ordersPage
            .open()
            .enterOrderId(nonExistentId)
            .getOrderErrorMessage(expectedError) shouldBe expectedError
    }
}