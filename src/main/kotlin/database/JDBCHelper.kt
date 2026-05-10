package org.example.database

import org.example.kotlin.general.Config
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import kotlin.use

class JDBCHelper {

//    private val jdbcUrl = "jdbc:postgresql://localhost:5432/playground"
//    private val username: String = "postgres"
//    private val password: String= "postgres"
    private val jdbcUrl = Config.getProps.jdbcUrl
    private val username = Config.getProps.username
    private val password = Config.getProps.password

    private val client = DriverManager.getConnection(jdbcUrl, username, password)

    fun getProducts(): List<Product> {
        val products = mutableListOf<Product>()

        try {
            val statement: Statement = client.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM table_products")

            while (resultSet.next()){
                val product = Product(
                    id = resultSet.getInt("id"),
                    name = resultSet.getString("name"),
                    description = resultSet.getString("description"),
                    price = resultSet.getDouble("price")
                )
            }

            resultSet.close()
            statement.close()
        } catch (e: Exception){
            println("Error fetching products ${e.message}")
        }

        return products
    }

    fun getProductNew() = client.use { connection ->
        connection.createStatement().use { statement ->
            statement.executeQuery("SELECT * FROM table_products").use { resultSet ->
                generateSequence { resultSet.takeIf { it.next() }?.toProduct() }.toList()
            }
        }
    }

    fun getUsers(): List<Users> {
        val users = mutableListOf<Users>()

        try {
            val statement: Statement = client.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM table_users")

            while (resultSet.next()){
                users.add((Users(
                    id = resultSet.getInt("id"),
                    username = resultSet.getString("username"),
                    email = resultSet.getString("email")
                )
                        )
                )
            }

            resultSet.close()
            statement.close()
        } catch (e: Exception){
            println("Error fetching users ${e.message}")
        }
        return users
    }

    fun getNewUser() = client.use { connection ->
        connection.createStatement().use { statement ->
            statement.executeQuery("SELECT * FROM table_users").use { resultSet ->
                generateSequence { resultSet.takeIf { it.next() }?.toUsers() }.toList()
            }
        }
    }
}

fun ResultSet.toProduct(): Product = Product(
    id = getInt("id"),
    name = getString("name"),
    description = getString("description"),
    price = getDouble("price")
)

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String
)

fun ResultSet.toUsers(): Users = Users(
    id = getInt("id"),
    username = getString("username"),
    email = getString("email"),
)

data class Users(
    var id: Int,
    var username: String,
    var email: String
)
