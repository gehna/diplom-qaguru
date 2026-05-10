package backend.helpers

import backend.api.models.orders.CreateOrderRequest
import backend.api.models.orders.CreateOrderResponse
import backend.api.models.orders.ProductOrderRequest
import backend.controllers.Controllers
import io.qameta.allure.Step
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.helpers.ProductHelper

class OrderHelperBackend : Controllers() {

    private val productHelper = ProductHelper()

    @Step("Create an order for test purposes")
    fun createOrderWithProduct(): CreateOrderResponse {
        val product = productHelper.createProductForOrder()
        return orders.createNewOrder(
            order = CreateOrderRequest(
                userId = null,
                products = listOf(ProductOrderRequest(product.id))
            )
        ).getAsObject()
    }
}