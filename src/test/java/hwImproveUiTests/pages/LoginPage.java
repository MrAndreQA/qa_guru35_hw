package hwImproveUiTests.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement
            userNameInput = $("#userName"),
            passwordInput = $("#password"),
            loginButton = $("#login");


    @Step("Открываем страницу авторизации")
    public LoginPage openLoginPage() {
        open("/login");
        return new LoginPage();
    }

    @Step("Вводим значение в поле UserName")
    public LoginPage fillUserNameInput(String login) {
        userNameInput.setValue(login);
        return new LoginPage();
    }

    @Step("Вводим значение в поле Password")
    public LoginPage fillPasswordInput(String password) {
        passwordInput.setValue(password);
        return new LoginPage();
    }

    @Step("Нажимаем на кнопку Login")
    public LoginPage clickLoginBtn() {
        loginButton.click();
        return new LoginPage();
    }
}