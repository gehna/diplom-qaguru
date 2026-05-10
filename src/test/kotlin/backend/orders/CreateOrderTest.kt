package backend.orders

import backend.api.models.ErrorResponse
import backend.api.models.orders.CreateOrderRequest
import backend.api.models.orders.ProductOrderRequest
import backend.api.models.orders.invalidOrderProduct
import backend.controllers.Controllers
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.extention.Extensions.Companion.getErrorAsObject
import org.example.backend.api.models.products.CreateProductRequest
import org.example.backend.api.models.users.createUser.defaultUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class CreateOrderTest : Controllers() {

    private fun newTestProduct(price: Double = 7.5) = products.createProduct(
        product = CreateProductRequest(
            name = "Coffee-${System.nanoTime()}".take(60),
            price = price,
            description = "Product for order test",
        )
    ).getAsObject()

    @Test
    @DisplayName("Backend: создание гостевого заказа с новым товаром возвращает корректный ответ")
    @Tags(Tag("backend"), Tag("regress"), Tag("orders"), Tag("smoke"))
    fun shouldCreateGuestOrderWithCustomProduct() {
        val product = newTestProduct(price = 7.5)

        val response = orders.createNewOrder(
            CreateOrderRequest(
                userId = null,
                products = listOf(ProductOrderRequest(product.id)),
            )
        )

        response.isSuccessful.shouldBeTrue()
        val order = response.getAsObject()

        order.id shouldBeGreaterThan 0
        order.products.map { it.id } shouldContain product.id
        order.totalAmount shouldBe product.price
        order.orderStatus shouldNotBe ""
    }

    @Test
    @DisplayName("Backend: заказ, созданный с userId, доступен через GET /orders/user/{id}")
    @Tags(Tag("backend"), Tag("regress"), Tag("orders"))
    fun shouldLinkOrderToUser() {
        val user = users.createUser(defaultUser()).getAsObject()
        val product = newTestProduct(price = 5.0)

        val order = orders.createNewOrder(
            CreateOrderRequest(
                userId = user.id,
                products = listOf(ProductOrderRequest(product.id)),
            )
        ).getAsObject()

        order.userId shouldBe user.id

        val userOrders = orders.getOrdersByUserId(user.id).getAsObject()
        userOrders.map { it.id } shouldContain order.id
    }

    @Test
    @DisplayName("Backend: отклонение заказа с несуществующим товаром")
    @Tags(Tag("backend"), Tag("regress"), Tag("orders"), Tag("negative"))
    fun shouldRejectOrderWithNonExistingProduct() {
        val response = orders.createNewOrder(
            CreateOrderRequest(
                userId = null,
                products = listOf(ProductOrderRequest(99_999_999)),
            )
        )

        response.isSuccessful.shouldBeFalse()
        response.getErrorAsObject<ErrorResponse>() shouldBe invalidOrderProduct
    }
}