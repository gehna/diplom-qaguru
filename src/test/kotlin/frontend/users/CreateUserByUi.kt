package frontend.users

import frontend.components.popup.JoinDialogPopup
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.example.backend.api.models.users.createUser.CreateUserErrors
import org.example.backend.api.models.users.createUser.defaultUser
import org.example.frontend.components.popup.CreateAccountPopup
import org.example.frontend.helpers.BaseUiTest
import org.example.frontend.pages.MainPage
import org.example.kotlin.frontend.helpers.UserHelper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import kotlin.random.Random

class CreateUserByUi: BaseUiTest() {
    private val userHelper = UserHelper()
    private val mainPage = MainPage()
    private val joinDialogPopup = JoinDialogPopup()
    private val createAccountPopup = CreateAccountPopup()

//    @Test
//    fun createUserViaUi() {
//        val creds = userHelper.generateUser()
//        userHelper.createUserViaUi(creds)
//    }


    @Test
    @DisplayName("Создать нового пользователя через UI с валидными кредами")
    @Tags(Tag("frontend"), Tag("regress"), Tag("users"), Tag("smoke"))
    fun shouldCreateUserWithValidData() {
        val username = "user_${Random.nextInt(100_000)}"
        val email = "user_${Random.nextInt(100_000)}@autotest.com"
        val password = "Password123!"

        println(mainPage.navigateHeader().getLinks())

        mainPage.navigateHeader().clickLink("Join")
        createAccountPopup.joinAs(username, email, password)
        mainPage.navigateHeader().checkUserPic().shouldBeTrue()
        userHelper.addUserToGC(email)
    }

    @Test
    @DisplayName("Проверить попап окно после клика на 'Join' кнопку в заголовке")
    @Tags(Tag("frontend"), Tag("regress"), Tag("users"))
    fun shouldOpenCreateAccountPopupWithCorrectTitle() {
        mainPage.navigateHeader().clickLink("Join")
        joinDialogPopup.getTitle() shouldBe "Create Account"
    }

    @Test
    @DisplayName("Падение при создании пользователя с уже существующим email")
    @Tags(Tag("frontend"), Tag("regress"), Tag("users"), Tag("negative"))
    fun shouldFailToCreateUserWithDuplicateEmail() {
        val existing = defaultUser()
        userHelper.users.createUser(existing)
        mainPage.navigateHeader().clickLink("Join")
        createAccountPopup.joinAs(
            username = "another_${Random.nextInt(100_000)}",
            email = existing.email,
            password = "Password123!"
        )
        createAccountPopup
            .getErrorMessage(CreateUserErrors.duplicateCredentials.reason) shouldBe
                CreateUserErrors.duplicateCredentials.reason
    }
    @Test
    @DisplayName("Падение при создании пользователя с пустой формой ввода")
    @Tags(Tag("frontend"), Tag("regress"), Tag("users"), Tag("negative"))
    fun shouldShowErrorWhenSubmittingEmptyForm() {
        mainPage.navigateHeader().clickLink("Join")
        createAccountPopup.joinAs("", "", "")
        createAccountPopup
            .getErrorMessage(CreateUserErrors.emptyCredentials.reason) shouldBe
                CreateUserErrors.emptyCredentials.reason
    }

    @Test
    @DisplayName("Падение на создании пользователя с невалидным email")
    @Tags(Tag("frontend"), Tag("regress"), Tag("users"), Tag("negative"))
    fun shouldShowErrorForInvalidEmailFormat() {
        mainPage.navigateHeader().clickLink("Join")
        createAccountPopup.joinAs(
            username = "user_${Random.nextInt(100_000)}",
            email = "not-an-email",
            password = "Password123!"
        )
        createAccountPopup
            .getErrorMessage(CreateUserErrors.invalidCredentials.reason) shouldBe
                CreateUserErrors.invalidCredentials.reason
    }

}