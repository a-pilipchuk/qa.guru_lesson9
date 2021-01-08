import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Browsers.FIREFOX;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TogglLoginUITests {
    @BeforeAll
    static void setup(){
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud:4444/wd/hub/";
        Configuration.browserSize = "1400x1000";
        //Configuration.browser = FIREFOX;
    }

    @AfterEach
    public void afterEach(){
        attachScreenshot("");
        attachPageSource();
        attachAsText("browser console log", getConsoleLogs());
        attachVideo();
        closeWebDriver();
    }

    @Tag("UI_test")
    @Test
    @DisplayName("Successful login")
    void TogglPositiveLoginTest() {
        //step("", () ->{});
        step("open login page", () ->{
            open("https://toggl.com/track/login/");
        });
        step("fill in email field", () ->{
            $("#email").val("qa.brest+1@gmail.com");
        });
        step("fill in password field and confirm", () ->{
            $("#password").val("5sffD9n9").pressEnter();
        });
        step("assert that 'timer' is visible on the page", () ->{
            $("a[href='/timer']").shouldBe(Condition.visible);
        });
        step("assert current URL equals expected URL", () ->{
            System.out.println(WebDriverRunner.url());
            assertEquals(WebDriverRunner.url(),"https://track.toggl.com/timer");
        });
    }

    @Tag("UI_test")
    @Test
    @DisplayName("Unsuccessful login")
    void TogglNegativeLoginTest() {
        //step("", () ->{});
        step("open login page", () ->{
            open("https://toggl.com/track/login/");
        });
        step("fill in email field", () ->{
            $("#email").val("qa.brest+1@gmail.com");
        });
        step("fill in password field with incorrect password and confirm", () ->{
            $("#password").val("1").pressEnter();
        });
        step("assert that 'timer' is visible on the page", () ->{
            $("a[href='/timer']").shouldBe(Condition.visible);
        });
    }
}
