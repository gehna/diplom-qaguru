package database

import backend.controllers.Controllers
import io.kotest.matchers.shouldBe
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.database.JDBCHelper
import org.example.frontend.components.popup.CreateAccountPopup
import org.example.frontend.helpers.BaseUiTest
import org.example.frontend.pages.MainPage
import org.example.kotlin.frontend.helpers.UserHelper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class DbCreateUser : BaseUiTest() {

    private val jdbcClient = JDBCHelper()
    private val userHelper = UserHelper()
    private val controllers = Controllers()

    @Test
    @Tags(Tag("DB"), Tag("regress"), Tag("users"))
    @DisplayName("Создать юзера и проверить через JDBC")
    fun testCreateUserWithJdbcHelper() {
        val suffix = System.currentTimeMillis()
        val username = "testDBuser_$suffix"
        val email = "testDBuser_$suffix@autotest.com"
        val password = "testDBuser"

        MainPage()
            .navigateHeader()
            .clickLink("Join")

        CreateAccountPopup()
            .joinAs(username, email, password)

        userHelper.addUserToGC(email)

        val dbUser = jdbcClient.waitForUserByEmail(email)

        dbUser.username shouldBe username
        dbUser.email shouldBe email

        val apiUser = controllers.users.getUserById(id = dbUser.id).getAsObject()

        apiUser.username shouldBe dbUser.username
        apiUser.email shouldBe dbUser.email
    }
}