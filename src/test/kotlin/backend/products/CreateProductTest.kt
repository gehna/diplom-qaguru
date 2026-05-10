package backend.products

import backend.api.models.ErrorResponse
import backend.api.models.products.invalidInput
import backend.controllers.Controllers
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.api.extention.Extensions.Companion.getErrorAsObject
import org.example.backend.api.models.products.CreateProductRequest
import org.example.backend.helpers.AuthorizationHelper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class CreateProductTest {

    private val controllers = Controllers()
    private val authHelper = AuthorizationHelper()

    private fun userToken(): String = authHelper.getNewToken()

    private fun validRequest(
        name: String = "Coffee-${System.nanoTime()}".take(60),
        price: Double = 12.99,
        description: String = "Fresh beans, ${System.nanoTime()}",
    ) = CreateProductRequest(name = name, price = price, description = description)

    @Test
    @DisplayName("Backend: создать продукт с валидными данными")
    @Tags(Tag("backend"), Tag("regress"), Tag("products"), Tag("smoke"))
    fun shouldCreateProductWithValidPayload() {
        val body = validRequest()
        val response = controllers.products.createProduct(token = userToken(), product = body)

        response.isSuccessful.shouldBeTrue()
        val created = response.getAsObject()

        created.id shouldBeGreaterThan 0
        created.name shouldBe body.name
        created.price shouldBe body.price
        created.description shouldBe body.description
    }

    @Test
    @DisplayName("Backend: продукт возвращенный через GET /products/{id} соответствует созданному")
    @Tags(Tag("backend"), Tag("regress"), Tag("products"))
    fun shouldReturnSameProductWhenFetchedByIdAfterCreate() {
        val body = validRequest()
        val created = controllers.products
            .createProduct(token = userToken(), product = body)
            .getAsObject()

        val fetched = controllers.products.getProductById(created.id).getAsObject()

        fetched shouldBe created
    }

    @Test
    @DisplayName("Backend: отмена создания если имя продукта превышает максимальную длину")
    @Tags(Tag("backend"), Tag("regress"), Tag("products"), Tag("negative"))
    fun shouldRejectCreateWhenNameTooLong() {
        val tooLongName = "N".repeat(61)
        val response = controllers.products.createProduct(
            token = userToken(),
            product = CreateProductRequest(
                name = tooLongName,
                price = 1.0,
                description = "D".repeat(100),
            ),
        )

        response.isSuccessful.shouldBeFalse()
                response.getErrorAsObject<ErrorResponse>() shouldBe invalidInput
    }

    @Test
    @DisplayName("Backend: отмена создания если описание продукта превышает максимальную длину")
    @Tags(Tag("backend"), Tag("regress"), Tag("products"), Tag("negative"))
    fun shouldRejectCreateWhenDescriptionTooLong() {
        val response = controllers.products.createProduct(
            token = userToken(),
            product = CreateProductRequest(
                name = "C".repeat(60),
                price = 1.0,
                description = "D".repeat(101),
            ),
        )

        response.isSuccessful.shouldBeFalse()
                response.getErrorAsObject<ErrorResponse>() shouldBe invalidInput
    }

    @Test
    @DisplayName("Backend: отмена создания если токен авторизации не валиден")
    @Tags(Tag("backend"), Tag("regress"), Tag("products"), Tag("negative"))
    fun shouldRejectCreateWhenTokenIsInvalid() {
        val response = controllers.products.createProduct(
            token = "Bearer definitely-not-a-valid-jwt",
            product = validRequest(),
        )

        response.isSuccessful.shouldBeFalse()
                response.code().shouldNotBe(200)
    }
}