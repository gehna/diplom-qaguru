package frontend.orders

import backend.api.models.orders.OrderStatus
import backend.api.models.orders.UpdateOrderRequest
import backend.controllers.Controllers
import backend.helpers.OrderHelperBackend
import frontend.pages.OrdersPage
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.helpers.AuthorizationHelper
import org.example.frontend.helpers.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class OrdersUpdateTest : BaseUiTest() {

    val orderHelper = OrderHelperBackend()
    val controllers = Controllers()
    val authHelper = AuthorizationHelper()

    @Test
    @DisplayName("Создать и проверить статус заказа через UI")
    @Tags(Tag("frontend"), Tag("regress"), Tag("orders"))
    fun orderStatusCheck() {
        val testOrder = orderHelper.createOrderWithProduct()

        val orderCheck = OrdersPage()
            .open()
            .enterOrderId(testOrder.id)
            .getOrderItems()
            .first { it.orderId == testOrder.id }

        orderCheck shouldNotBe null
        orderCheck.orderId shouldBe testOrder.id
        orderCheck.status shouldBe OrderStatus.PENDING.name
    }

    @Test
    @DisplayName("Создать и обновить статус заказа, проверить новый статус через UI")
    @Tags(Tag("frontend"), Tag("regress"), Tag("orders"))
    fun orderStatusUpdateCheck() {

        val userToken = authHelper.getNewToken()
        val testOrder = orderHelper.createOrderWithProduct()

        testOrder.orderStatus shouldBe OrderStatus.PENDING.name

        val updatedOrder = controllers.orders.updateOrderById(
            token = userToken,
            id = testOrder.id,
            body = UpdateOrderRequest(orderStatus = OrderStatus.IN_PROGRESS)
        ).getAsObject()

        updatedOrder.orderStatus shouldNotBe OrderStatus.PENDING.name

        val orderUpdate = OrdersPage()
            .open()
            .enterOrderId(updatedOrder.id)
            .getOrderItems()
            .first { it.orderId == updatedOrder.id }

        orderUpdate shouldNotBe null
        orderUpdate.orderId shouldBe testOrder.id
        orderUpdate.status shouldBe OrderStatus.IN_PROGRESS.name
    }
}