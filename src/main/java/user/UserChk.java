package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;

public class UserChk {

    @Step("Создание пользователя")
    public void CreateUserOk(ValidatableResponse createResponse){
        createResponse
                .assertThat()
                .body("success",equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Логин Ок")
    public void loginUserOk(ValidatableResponse loginResponse){
        loginResponse
                .assertThat()
                .body("success",equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Создание пользователя, который уже зарегистрирован")
    public void createSameUser(ValidatableResponse sameUserResponse){
        sameUserResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Создание пользователя и не заполнить одно из обязательных полей")
    public void createUserWithoutField(ValidatableResponse withoutFieldResponse){
        withoutFieldResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Логин с неверным логином и паролем")
    public void loginWrongCreditinals(ValidatableResponse loginResponse){
        loginResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Изменение данных пользователя с авторизацией")
    public void updateUser(ValidatableResponse changeResponse){
        changeResponse
                .assertThat()
                .body("success",equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Изменение данных пользователя без авторизации")
    public void updateUserNoAuth(ValidatableResponse changeResponse){
        changeResponse
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
