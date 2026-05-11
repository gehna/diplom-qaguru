package backend.orders

import backend.api.models.ErrorResponse
import backend.api.models.orders.CreateOrderRequest
import backend.api.models.orders.OrderStatus
import backend.api.models.orders.ProductOrderRequest
import backend.api.models.orders.UpdateOrderRequest
import backend.api.models.orders.wrongOrderStatus
import backend.controllers.Controllers
import io.kotest.matchers.shouldBe
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.extention.Extensions.Companion.getErrorAsObject
import org.example.backend.helpers.AuthorizationHelper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class UpdateOrderTest : Controllers() {

    private val authHelper = AuthorizationHelper()

    @Test
    @DisplayName("Тест на обновление статуса заказа")
    @Tags(Tag("regress"), Tag("backend"), Tag("orders"))
    fun updateOrderStatusCheck() {
        val userToken = authHelper.getNewToken()

        val order = orders.createNewOrder(
            CreateOrderRequest(null, listOf(ProductOrderRequest(1)))
        ).getAsObject()

        val updatedOrder = orders.updateOrderById(
            token = userToken,
            id = order.id,
            body = UpdateOrderRequest(orderStatus = OrderStatus.IN_PROGRESS)
        ).getAsObject()

        updatedOrder.orderStatus shouldBe OrderStatus.IN_PROGRESS.name
        updatedOrder.id shouldBe order.id
    }

    @DisplayName("Тест на обновление статуса заказа всеми статусами")
    @Tags(Tag("regress"), Tag("backend"), Tag("orders"))
    @ParameterizedTest(name = "Update order status to: {0}")
    @EnumSource(value = OrderStatus::class, names = ["PENDING", "IN_PROGRESS", "COMPLETED"])
    fun updateOrderStatusParametrizedCheck(status: OrderStatus) {
        val userToken = authHelper.getNewToken()

        val order = orders.createNewOrder(
            order = CreateOrderRequest(null, listOf(ProductOrderRequest(1)))
        ).getAsObject()

        val updatedOrder = orders.updateOrderById(
            token = userToken,
            id = order.id,
            body = UpdateOrderRequest(orderStatus = status)
        ).getAsObject()

        updatedOrder.orderStatus shouldBe status.name
        updatedOrder.id shouldBe order.id
    }

    @Test
    @DisplayName("Тест на обновление заказа невалидным статусом")
    @Tags(Tag("regress"), Tag("backend"), Tag("orders"))
    fun updateNonexistentOrderStatusCheck() {
        val userToken = authHelper.getNewToken()

        val order = orders.createNewOrder(
            CreateOrderRequest(null, listOf(ProductOrderRequest(1)))
        ).getAsObject()

        val updatedOrder = orders.updateOrderById(
            token = userToken,
            id = order.id,
            body = UpdateOrderRequest(orderStatus = OrderStatus.UNKNOWN)
        )

        val error = updatedOrder.getErrorAsObject<ErrorResponse>()
        error shouldBe wrongOrderStatus
    }
}