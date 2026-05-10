package backend.api.models.orders

data class CreateOrderRequest(
    var userId: Int?,
    var products: List<ProductOrderRequest>
)

data class ProductOrderRequest(
    var id: Int
)