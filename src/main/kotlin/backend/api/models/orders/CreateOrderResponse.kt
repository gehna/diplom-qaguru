package backend.api.models.orders

import org.example.backend.api.models.products.CreateProductResponse

data class CreateOrderResponse(
    var createdAt: Long,
    var id: Int,
    var orderStatus: String,
    var products: List<CreateProductResponse>,
    var totalAmount: Double,
    var updatedAt: Long,
    var userId: Int
)