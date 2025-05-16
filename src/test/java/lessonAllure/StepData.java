package lessonAllure;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StepData {
        public StepData openGitHubPage() {
            open("https://github.com");
            return this;
        }

        @Step("Ищем репозиторий")
        public StepData searchForRepository(String repositoryName) {
            $(".search-input-container").click();
            $("#query-builder-test").sendKeys(repositoryName);
            $("#query-builder-test").submit();
            return this;
        }

        @Step("Переходим по репозиторию")
        public StepData clickOnRepository(String repositoryName) {
            $("[href='/" + repositoryName + "']").click();
            return this;
        }

        @Step("Проверяем название 'Issues' в репозитории")
        public StepData checkIssue() {
            $("[data-content=Issues]").shouldHave(text("Issues"));
            return this;
        }
    }

