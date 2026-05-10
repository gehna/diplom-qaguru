package org.example.kotlin.general

import java.util.Properties

object Config {
    private val DEFAULT_PROP_FILE = "/example.properties"

    val getProps: Props by lazy {
        val fileName = System.getProperty("env_config", DEFAULT_PROP_FILE)

        val properties = Properties().apply {
            val stream = Config::class.java.getResourceAsStream(fileName)
                ?: throw IllegalStateException("Properties file '$fileName' not found")
            stream.use { load(it) }
        }

        fun Properties.getRequiredProperty(key: String): String {
            return getProperty(key)
                ?: throw IllegalStateException("Required property '$key' not found in '$fileName'")
        }

        Props(
            browserName = properties.getRequiredProperty("browser.name"),
            browserVersion = properties.getRequiredProperty("browser.version"),
            frontendUrl = properties.getRequiredProperty("frontend.url"),
            backendUrl = properties.getRequiredProperty("backend.url"),
            moonHost = properties.getRequiredProperty("moon.host"),
            jdbcUrl = properties.getRequiredProperty("postgres.jdbcUrl"),
            username = properties.getRequiredProperty("postgres.username"),
            password = properties.getRequiredProperty("postgres.password"),
        )
    }
}

data class Props(
    val browserName: String,
    val browserVersion: String,
    val frontendUrl: String,
    val backendUrl: String,
    val moonHost: String,
    val jdbcUrl: String,
    val username: String,
    val password: String
)
