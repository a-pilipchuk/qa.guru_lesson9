import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToggleLoginUITests {
    @BeforeAll
    static void setup(){
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.browserSize= "1400x900";
    }

    @AfterEach
    public void afterEach(){
        attachScreenshot("");
        attachPageSource();
        attachAsText("browser console log", getConsoleLogs());
        closeWebDriver();
    }

    @Tag("UI_test")
    @Test
    @DisplayName("Successful login")
    void ToggleLoginTest() {
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
}
