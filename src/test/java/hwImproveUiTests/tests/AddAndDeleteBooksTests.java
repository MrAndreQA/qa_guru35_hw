package hwImproveUiTests.tests;

import com.codeborne.selenide.Selenide;
import hwImproveUiTests.models.addBook.AddBookBodyModel;
import hwImproveUiTests.models.login.LoginBodyModel;
import hwImproveUiTests.models.login.LoginResponseModel;
import hwImproveUiTests.pages.ProfilePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import java.util.List;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static hwImproveUiTests.specs.BaseSpec.requestSpec;
import static hwImproveUiTests.specs.BaseSpec.responseSpec;
import static hwImproveUiTests.tests.TestData.login;
import static hwImproveUiTests.tests.TestData.password;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class AddAndDeleteBooksTests extends RemoteTestBase {
    ProfilePage profilePage = new ProfilePage();

    @DisplayName("Book Store: проверка удаления книги из коллекции")
    @Feature("Раздел - Profile ")
    @Story("Profile - удаление книг")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "Book Store - Profile", url = "https://demoqa.com/profile")
    @Owner("Volodin_AS")
    @Test
    void successfulAddBookFromUserCollectionTest() {
        LoginBodyModel loginData = new LoginBodyModel();
        loginData.setUserName(login);
        loginData.setPassword(password);

        LoginResponseModel authResponse = step("Make login request", () ->
                given(requestSpec)
                        .body(loginData)

                        .when()
                        .post(LOGIN_ENDPOINT)

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(LoginResponseModel.class));

        step("Delete all books from User Collection", () -> {
            given(requestSpec)
                    .header("Authorization", "Bearer " + authResponse.getToken())
                    .queryParams("UserId", authResponse.getUserId())
                    .when()
                    .delete(BOOK_STORE_ENDPOINT)
                    .then()
                    .spec(responseSpec(204));
        });


        AddBookBodyModel addBookData = new AddBookBodyModel(
                authResponse.getUserId(),
                List.of(new AddBookBodyModel.IsbnItem("9781449325862")));

        step("Add book to UserCollection", () -> {
            given(requestSpec)
                    .header("Authorization", "Bearer " + authResponse.getToken())
                    .body(addBookData)
                    .when()
                    .post(BOOK_STORE_ENDPOINT)
                    .then()
                    .spec(responseSpec(201));
        });

        step("Set loginCookies on UI", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        });

        step("Delete book from User Collection and check empty list", () -> {
            profilePage.open()
                    .checkUserNameValue(login)
                    .clickOnDeleteFirstBookBtn()
                    .clickBtnOkInModal();
            Selenide.confirm();
            profilePage.checkListofBooksIsEmpty();
        });
    }
}