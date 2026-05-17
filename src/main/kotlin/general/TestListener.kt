package org.example.general


import backend.controllers.Controllers
import com.codeborne.selenide.Screenshots
import com.codeborne.selenide.Selenide
import io.qameta.allure.Attachment
import org.example.backend.api.extention.Extensions.Companion.getAsObject
import org.example.backend.helpers.AuthorizationHelper
import org.example.backend.helpers.GarbageCollector

import org.example.kotlin.general.Config
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestIdentifier
import org.junit.platform.launcher.TestPlan

class TestListener : Controllers(), TestExecutionListener {
    private val authHelper = AuthorizationHelper()

    override fun testPlanExecutionStarted(testPlan: TestPlan) {
        println("|-----Test plan started. Total tests: ${testPlan.countTestIdentifiers { it.isTest }} ----|")
        println("Initializing Configurations...").also { Config.getProps }
        println(Config.getProps)
    }

    override fun executionSkipped(testIdentifier: TestIdentifier, reason: String) {
        if (testIdentifier.isTest) println("Ignoring test: {${testIdentifier.displayName} reason: $reason}")
    }

    override fun executionStarted(testIdentifier: TestIdentifier) {
        if (testIdentifier.isTest) {
            println("STARTED: ${testIdentifier.displayName}")
        }
    }

    override fun testPlanExecutionFinished(testPlan: TestPlan) {
        println("|-----Test plan finished-----|")
        Selenide.closeWebDriver()

        println("|------ Garbage collector -------|")
        users.getAllUsers(token = authHelper.getAdminToken(), offset = 0, limit = 50).getAsObject().forEach { user ->
            if (user.email.contains("@autotest.com")) {
                users.deleteUserById(token = authHelper.getAdminToken(), id = user.id)
                    .also { println("Deleted user: ${user.email}") }
            }
        }

        GarbageCollector.order.forEach { id ->
            orders.deleteOrder(token = authHelper.getAdminToken(), id = id)
                .also {
                    val resp = orders.deleteOrder(token = authHelper.getAdminToken(), id = id)
                    println("Delete order $id -> code=${resp.code()} success=${resp.isSuccessful}")
                    println("Deleted order: $id")
                }
        }

        GarbageCollector.products.forEach { id ->
            products.deleteProductById(token = authHelper.getAdminToken(), id = id)
                .also { println("Deleted product: $id") }
        }
    }

    override fun executionFinished(testIdentifier: TestIdentifier, testExecutionResult: TestExecutionResult) {
        if (testIdentifier.isTest) println("Finished test: ${testIdentifier.displayName} - Result: ${testExecutionResult.status}")
        if (testExecutionResult.status == TestExecutionResult.Status.FAILED && testIdentifier.displayName != "JUnit Jupiter") {
            attchScreenshot()
        }
    }

    @Attachment(value = "{name}", type = "image/png")
    fun attchScreenshot(name: String = "SCREENSHOT"): ByteArray? {
        return Screenshots.takeScreenShotAsFile()?.readBytes()
    }


}