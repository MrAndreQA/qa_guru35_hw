package hwImproveUiTests.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import hwImproveUiTests.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.Map;

public class RemoteTestBase {

    public static String LOGIN_ENDPOINT = "/Account/v1/Login";
    public static String BOOK_STORE_ENDPOINT = "/BookStore/v1/Books";

    public static void setupRemote() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "https://" +
                System.getProperty("selenoidUserLogin") + ":" +
                System.getProperty("selenoidUserPassword") + "@" +
                System.getProperty("selenoidRemoteServerUrl") + "/wd/hub";
    }

    @BeforeAll
    public static void beforeAll() {
        Configuration.browser = System.getProperty("browser");
        Configuration.browserVersion = System.getProperty("browserVersion");
        Configuration.browserSize = System.getProperty("browserResolution");
        Configuration.baseUrl = "https://usetech.ru";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;
        setupRemote();
    }

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Скриншот результата теста");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}