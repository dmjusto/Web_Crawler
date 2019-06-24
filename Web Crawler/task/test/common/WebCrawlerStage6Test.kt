package common

import org.hyperskill.hstest.v4.stage.SwingTest
import org.hyperskill.hstest.v4.testcase.CheckResult
import org.hyperskill.hstest.v4.testcase.TestCase

import crawler.WebCrawler

abstract class WebCrawlerStage6Test : SwingTest<WebCrawlerClue>(WebCrawler()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.toggleButton(it) }
        val saveButton = ComponentRequirements("ExportButton", isEnabled = true) { window.button(it) }
        val pathToFileText = ComponentRequirements("ExportUrlTextField", isEnabled = true) { window.textBox(it) }

        val depthText = ComponentRequirements("DepthTextField", isEnabled = true) { window.textBox(it) }
        val depthCheckBox = ComponentRequirements("DepthCheckBox", isEnabled = true) { window.checkBox(it) }
        val parsedLabel = ComponentRequirements("ParsedLabel", isEnabled = true) { window.label(it) }

        return frameTests(::frame) +
                existenceTests(urlText, getButton, saveButton, pathToFileText, depthText, depthCheckBox, parsedLabel) +
                componentsAreEnabledTests(
                    urlText,
                    getButton,
                    saveButton,
                    pathToFileText,
                    depthText,
                    depthCheckBox,
                    parsedLabel
                ) +
                stage5TestsWithJToggleButton(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 1
                ) +
                stage5TestsWithJToggleButton(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 2
                ) +
                stage5TestsWithJToggleButton(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 100500
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}
