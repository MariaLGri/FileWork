package lessonAllure;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;


public class SelenideTest {
    private static final String REPOSITORY = "MariaLGri/FileWork";

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
  @Test
  @DisplayName("Проверка наименования 'Issues' selenide чисто")
    public void testIssueSearch() {
        open("https://github.com");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        //sleep(3000);
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("[data-content=Issues]").shouldHave(text("Issues"));
    }

    @Test
    @DisplayName("Проверка наименования 'Issues' лямбда способом")
    public void lambdaStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });

        step("Ищем репозиторий " + REPOSITORY, () -> {
            $("[data-target='qbsearch-input.inputButtonText']").click();
            $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        });

        step("Переходим по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });

        step("Проверяем название 'Issues' в репозитории", () ->
                $("[data-content=Issues]").shouldHave(text("Issues")));
    }

    @Test
    @DisplayName("Проверка наименования 'Issues' через аннотацию @Step из файла StepData")
    public void annotatedStepsDateTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

         StepData testStep = new StepData();
        testStep.openGitHubPage();
        testStep.searchForRepository(REPOSITORY);
        testStep.clickOnRepository(REPOSITORY);
        testStep.checkIssue();
    }
}
