package backend.controllers

import backend.api.models.orders.CreateOrderRequest
import backend.api.models.orders.CreateOrderResponse
import backend.api.models.orders.UpdateOrderRequest
import io.qameta.allure.Step
import okhttp3.ResponseBody
import org.example.backend.api.endpoints.Endpoints
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.helpers.AuthorizationHelper
import org.example.backend.helpers.GarbageCollector
import retrofit2.Response

class OrderController : Endpoints() {

    val authHelper = AuthorizationHelper()

    @Step("Get all orders")
    fun getAllOrders(
        token: String = authHelper.getAdminToken(),
        offset: Int = 0,
        limit: Int = 50
    ): Response<List<CreateOrderResponse>> {
        return orders.getAllOrders(token, offset, limit).execute()
    }

    @Step("Get order with id: {id}")
    fun getOrdersById(id: Any): Response<CreateOrderResponse> {
        return orders.getOrderById(id).execute()
    }

    @Step("Get order with user id: {id}")
    fun getOrdersByUserId(id: Any): Response<List<CreateOrderResponse>> {
        return orders.getOrderByUserId(id).execute()
    }

    @Step("Create a new order")
    fun createNewOrder(order: CreateOrderRequest): Response<CreateOrderResponse> {
        return orders.createOrder(order).execute()
            .also { if (it.isSuccessful) GarbageCollector.order.add(it.getAsObject().id) }
    }

    @Step("Update order status")
    fun updateOrderById(
        token: String = authHelper.getAdminToken(),
        id: Int,
        body: UpdateOrderRequest
    ): Response<CreateOrderResponse> {
        return orders.updateOrderStatus(token, id, body).execute()
    }

    @Step("Delete order id: {id}")
    fun deleteOrder(token: String = authHelper.getAdminToken(), id: Int): Response<ResponseBody> {
        return orders.deleteOrderById(token, id).execute()
    }
}