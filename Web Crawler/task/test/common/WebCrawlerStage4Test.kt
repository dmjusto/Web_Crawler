package common

import org.assertj.swing.fixture.JButtonFixture
import org.assertj.swing.fixture.JTableFixture
import org.assertj.swing.fixture.JTextComponentFixture
import org.hyperskill.hstest.v4.common.Utils.sleep
import org.hyperskill.hstest.v4.stage.SwingTest
import org.hyperskill.hstest.v4.testcase.CheckResult
import org.hyperskill.hstest.v4.testcase.TestCase

import crawler.WebCrawler

abstract class WebCrawlerStage4Test : SwingTest<WebCrawlerClue>(WebCrawler()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val titlesTable = ComponentRequirements("TitlesTable", isEnabled = false) { window.table(it) }
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.button(it) }
        val titleLabel = ComponentRequirements("TitleLabel", isEnabled = true) { window.label(it) }

        return frameTests(::frame) +
                existenceTests(titlesTable, urlText, getButton, titleLabel) +
                componentsAreEnabledTests(titlesTable, urlText, getButton, titleLabel) +
                stage3Tests(
                    titleLabelRequirements = titleLabel,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                ) +
                stage4Tests(
                    titlesTableRequirements = titlesTable,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage4Tests(
    titlesTableRequirements: ComponentRequirements<JTableFixture>,
    getTextButtonRequirements: ComponentRequirements<JButtonFixture>,
    locationTextFieldRequirements: ComponentRequirements<JTextComponentFixture>
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest {
            val locationTextField = locationTextFieldRequirements.requireExistingComponent()
            val getTextButton = getTextButtonRequirements.requireExistingComponent()
            val titleTable = titlesTableRequirements.requireExistingComponent()

            for (url in pages.keys) {
                locationTextField.setText(url)

                getTextButton.click()

                sleep(100)

                val contents = titleTable.contents()

                if (contents.any { it.size != 2 }) {
                    return@createWebCrawlerTest fail("Table your app shows has a wrong columns number")
                }

                if (contents.size != url.deepUrls(depth = 1).size) {
                    return@createWebCrawlerTest fail("Table your app shows has a wrong rows number" +
                            " have ${contents.size} should ${ url.deepUrls(depth = 1).size}")
                }

                for ((writtenUrl, writtenTitle) in contents) {
                    if (pages.getValue(writtenUrl).title != writtenTitle) {
                        return@createWebCrawlerTest fail("Table your app shows has a wrong row")
                    }
                }
            }

            return@createWebCrawlerTest CheckResult(true)
        }.withLocalhostPagesOn(PORT)
    )
}
