import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import org.example.frontend.helpers.Extenstions.Companion.toMoney
import org.example.frontend.helpers.Wrappers.Companion.byDataTestGroup

class CartItems(val items: ElementsCollection) {

    fun getItems(): List<CartItem> {
        return items
            .map { CartItem(
                image = it.find(byDataTestGroup("cart-item-image")),
                name = it.find(byDataTestGroup("cart-item-name")).text,
                price = it.find(byDataTestGroup("cart-item-unit-price")).text.toMoney(),
                totalPrice = it.find(byDataTestGroup("cart-item-price")).text.toMoney(),
                btnIncrement = it.find(byDataTestGroup("cart-item-increment")),
                btnDecrement = it.find(byDataTestGroup("cart-item-decrement")),
                btnRemoveItem = it.find(byDataTestGroup("cart-item-remove")),
                quantity = it.find(byDataTestGroup("cart-item-qty")).text.toInt(),
            ) }
    }
}

data class CartItem(
    val image: SelenideElement,
    val name: String,
    val price: Double,
    val totalPrice: Double,
    val btnIncrement: SelenideElement,
    val btnDecrement: SelenideElement,
    val btnRemoveItem: SelenideElement,
    var quantity: Int,
)