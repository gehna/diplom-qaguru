package frontend.helpers

import backend.controllers.Controllers
import io.qameta.allure.Step
import org.example.backend.helpers.GarbageCollector

class OrderHelperFrontend : Controllers() {

    @Step("Find order by id and add it to GC")
    fun addOrderToGC(orderId: Int) {
        GarbageCollector.order.add(orderId)
        println("Added to GC order: $orderId")
    }
}