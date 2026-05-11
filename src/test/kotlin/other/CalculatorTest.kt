package other

import frontend.helpers.other.Calculator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CalculatorTest {

    private val calculator = Calculator()

    @Test
    @DisplayName("Сумма двух положительных чисел")
    @Tags(Tag("unit"), Tag("regress"), Tag("calculator"))
    fun addPositiveNumbers() {
        calculator.add(2, 3) shouldBe 5
    }

    @Test
    @DisplayName("Разность отрицательных чисел")
    @Tags(Tag("unit"), Tag("regress"), Tag("calculator"))
    fun subtractNegativeNumbers() {
        calculator.subtract(-2, -5) shouldBe 3
    }

    @Test
    @DisplayName("Умножение на ноль возвращает ноль")
    @Tags(Tag("unit"), Tag("regress"), Tag("calculator"))
    fun multiplyByZero() {
        calculator.multiply(123, 0) shouldBe 0
    }

    @Test
    @DisplayName("Деление на ноль выбрасывает исключение")
    @Tags(Tag("unit"), Tag("regress"), Tag("calculator"), Tag("negative"))
    fun divideByZeroThrows() {
        shouldThrow<IllegalArgumentException> {
            calculator.divide(10, 0)
        }
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @DisplayName("Параметризованный тест сложения")
    @Tags(Tag("unit"), Tag("regress"), Tag("calculator"))
    @CsvSource(
        "1, 1, 2",
        "10, 20, 30",
        "-5, 5, 0",
        "100, -50, 50"
    )
    fun addParametrized(a: Int, b: Int, expected: Int) {
        calculator.add(a, b) shouldBe expected
    }
}