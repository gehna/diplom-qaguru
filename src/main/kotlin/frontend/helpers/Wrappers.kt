package org.example.frontend.helpers

import com.codeborne.selenide.Selectors

class Wrappers {

    companion object {
        fun byDataTestGroup(target: String) = Selectors.by("data-test-group", target)

        fun byDataTestId(target: String) = Selectors.by("data-test-id", target)
    }
}