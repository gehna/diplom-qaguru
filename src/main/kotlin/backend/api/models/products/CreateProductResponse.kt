package org.example.backend.api.models.products

data class CreateProductResponse(
    var id: Int,
    var name: String,
    var price: Double,
    var description: String
)