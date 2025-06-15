package hwImproveUiTests.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    private final SelenideElement
            userNameValue = $("#userName-value"),
            deleteFirstBookBtn = $("#delete-record-undefined"),
            btnOkInModal = $("#closeSmallModal-ok"),
                    noRowsFound = $(".rt-noData");

    @Step("Открываем страницу Profile")
    public ProfilePage open() {
        Selenide.open("/profile");
        return new ProfilePage();
    }

    @Step("Проверяем значение в UserName")
    public ProfilePage checkUserNameValue(String login) {
        userNameValue.shouldHave(text(login));
        return new ProfilePage();
    }

    @Step("Нажимаем на кнопку удаления первой книги в коллекции")
    public ProfilePage clickOnDeleteFirstBookBtn() {
        deleteFirstBookBtn.click();
        return new ProfilePage();
    }

    @Step("Нажимаем на кнопку Ok в модальном окне")
    public ProfilePage clickBtnOkInModal() {
        btnOkInModal.click();
        return new ProfilePage();
    }

    @Step("Проверяем, что список книг пуст")
    public ProfilePage checkListofBooksIsEmpty() {
        noRowsFound.isDisplayed();
        noRowsFound.shouldHave(text("No rows found"));
        return new ProfilePage();
    }
}