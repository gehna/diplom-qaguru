package org.example.backend.api.endpoints

import backend.api.endpoints.OrdersEndpoints
import org.example.backend.api.RetrofitClient

open class Endpoints {
    protected val auth: AuthEndpoints by lazy { RetrofitClient.createService(AuthEndpoints::class.java) }
    protected val users: UserEndpoints by lazy { RetrofitClient.createService(UserEndpoints::class.java) }
    protected val products: ProductsEndpoints by lazy { RetrofitClient.createService(ProductsEndpoints::class.java) }
    protected val orders: OrdersEndpoints by lazy { RetrofitClient.createService(OrdersEndpoints::class.java) }
}