package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Model;

import static praktikum.Const.*;

public class UserApi extends Model {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user){
        return spec()
                .body(user)
                .when()
                .post(REG_USER)
                .then()
                .log()
                .all();
    }

    @Step("Получение токена")
    public String getToken(ValidatableResponse createResponse){
        return createResponse
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken){
        spec()
                .header("authorization", accessToken)
                .when()
                .delete(UPD_USER)
                .then()
                .log()
                .all();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserCreds creds){
        return spec()
                .body(creds)
                .when()
                .post(LOG_USER)
                .then()
                .log()
                .all();
    }

    @Step("Изменение данных пользователя c авторизацией")
    public ValidatableResponse updateUser(String accessToken, User user){
        return spec()
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(UPD_USER)
                .then()
                .log()
                .all();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse updateUserNoAuth(User user){
        return spec()
                .body(user)
                .when()
                .patch(UPD_USER)
                .then()
                .log()
                .all();
    }

}
