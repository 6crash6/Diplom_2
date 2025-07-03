package test;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import user.User;
import user.UserApi;
import user.UserChk;
import user.UserCreds;

@ExtendWith(AllureJunit5.class)
public class TestUpdateUser {

    private String accessToken;
    private User user;
    private final UserApi userApi = new UserApi();
    private final UserChk userChk = new UserChk();
    private String randomEmail;
    private String randomPassword;
    private String randomName;

    @BeforeEach
    public void setUp() {
        user = User.random();


        String randomUserMail = RandomStringUtils.randomAlphabetic(5);
        String randomDomainMail = RandomStringUtils.randomAlphabetic(5) + ".com";
        randomEmail = randomUserMail + "@" + randomDomainMail;

        randomPassword = RandomStringUtils.randomAlphanumeric(8);
        randomName = RandomStringUtils.randomAlphabetic(6, 10);

        ValidatableResponse responseCreate = userApi.createUser(user);
        userChk.CreateUserOk(responseCreate);

        var creds = UserCreds.from(user);
        ValidatableResponse responseLogin = userApi.loginUser(creds);
        userChk.loginUserOk(responseLogin);

        accessToken = userApi.getToken(responseLogin);
    }

    @AfterEach
    public void cleanUp() {
        if (accessToken != null) {
            userApi.deleteUser(accessToken);
        }
    }

    @DisplayName("Изменение данных пользователя (e-mail) с авторизацией")
    @Test
    public void updUserEmail() {
        user.setEmail(randomEmail);
        ValidatableResponse response = userApi.updateUser(accessToken, user);
        userChk.updateUser(response);
    }

    @DisplayName("Изменение данных пользователя (password) с авторизацией")
    @Test
    public void updUserPass() {
        user.setPassword(randomPassword);
        ValidatableResponse response = userApi.updateUser(accessToken, user);
        userChk.updateUser(response);
    }

    @DisplayName("Изменение данных пользователя (name) с авторизацией")
    @Test
    public void updUserName() {
        user.setName(randomName);
        ValidatableResponse response = userApi.updateUser(accessToken, user);
        userChk.updateUser(response);
    }

    @DisplayName("Изменение данных пользователя (e-mail) без авторизации")
    @Test
    public void updUserEmailNoAth() {
        user.setEmail(randomEmail);
        ValidatableResponse response = userApi.updateUserNoAuth(user);
        userChk.updateUserNoAuth(response);
    }

    @DisplayName("Изменение данных пользователя (password) без авторизации")
    @Test
    public void updUserPassNoAuth(){
        user.setPassword(randomPassword);
        ValidatableResponse response = userApi.updateUserNoAuth(user);
        userChk.updateUserNoAuth(response);
    }

    @DisplayName("Изменение данных пользователя (name) без авторизации")
    @Test
    public void updUserNameNoAuth(){
        user.setName(randomName);
        ValidatableResponse response = userApi.updateUserNoAuth(user);
        userChk.updateUserNoAuth(response);
    }
}