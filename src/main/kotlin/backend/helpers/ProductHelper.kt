package org.example.backend.helpers

import backend.controllers.Controllers
import io.qameta.allure.Step
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.models.products.CreateProductRequest
import org.example.backend.api.models.products.CreateProductResponse

class ProductHelper: Controllers() {

    @Step("Create a number of coffee products: {count}")
    fun createCoffeeProduct(count: Int): List<CreateProductRequest> {
        val listOfProducts = mutableListOf<CreateProductRequest>()
        for (i in 1..count){
            listOfProducts.add(CreateProductRequest("Coffee #$i", description = "Description for coffee product #$i", price = i.toDouble()))
        }

        listOfProducts.forEach {
            products.createProduct(product = it)
        }

        return listOfProducts.toList()
    }

    @Step("Add product if it doesn't exist")
    fun addProduct(name: String, count: Int, token: String): List<CreateProductRequest> {
        val existingProducts = products.getProducts().getAsObject()
            .filter { it.name.contains(name, ignoreCase = true) }

        if (existingProducts.isNotEmpty()) return existingProducts.map {
            CreateProductRequest(name = it.name, price = it.price, description = "Description for product #$name")
        }

        val addedProducts = mutableListOf<CreateProductRequest>()
        repeat(count){i ->
            val product = CreateProductRequest(
                name = "${name} #$i",
                description = "Description for product #$i",
                price = i.toDouble()
            )
            products.createProduct(token = token, product = product)
            addedProducts.add(product)
        }
        return addedProducts.toList()
    }

    @Step("Create a number of tea products: {count}")
    fun createTeaProduct(count: Int): List<CreateProductRequest> {
        val listOfProducts = mutableListOf<CreateProductRequest>()
        for (i in 1..count){
            listOfProducts.add(CreateProductRequest("Tea #$i", description = "Description for tea product #$i", price = i.toDouble()))
        }

        listOfProducts.forEach {
            products.createProduct(product = it)
        }

        return listOfProducts.toList()
    }

    @Step("Create product for order")
    fun createProductForOrder(): CreateProductResponse {
        return products.createProduct(
            product = CreateProductRequest(
                name = "Test product",
                price = 3.0,
                description = "Test product for orders"
            )
        ).getAsObject()
    }
}