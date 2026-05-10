package backend.controllers

import org.example.backend.controllers.ProductController
import org.example.backend.controllers.UsersController
import org.example.kotlin.backend.controllers.AuthController

open class Controllers {
    protected val auth get() = AuthController()
    val users get() = UsersController()
    val products get() = ProductController()
    val orders get() = OrderController()
}