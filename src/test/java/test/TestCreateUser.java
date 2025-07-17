package test;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import user.User;
import user.UserApi;
import user.UserChk;

@ExtendWith(AllureJunit5.class)
public class TestCreateUser {

    private String accessToken;
    private final UserApi userApi = new UserApi();
    private final UserChk userChk = new UserChk();
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.random();
    }

    @AfterEach
    public void cleanUp() {
        if (accessToken != null) {
            userApi.deleteUser(accessToken);
        }
    }

    @DisplayName("Создание пользователя")
    @Test
    public void createUser() {
        ValidatableResponse response = userApi.createUser(user);
        userChk.createUserOk(response);
        accessToken = userApi.getToken(response);
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
    public void createExistUser() {
        ValidatableResponse response = userApi.createUser(user);
        userChk.createUserOk(response);

        ValidatableResponse response1 = userApi.createUser(user);
        userChk.createSameUser(response1);

        accessToken = userApi.getToken(response);
    }

    @DisplayName("создать пользователя и не заполнить одно из обязательных полей (e-mail)")
    @Test
    public void createUserNoMail() {
        User userNoMail = User.random();
        userNoMail.setEmail("");
        ValidatableResponse response = userApi.createUser(userNoMail);
        userChk.createUserWithoutField(response);
    }

    @DisplayName("создать пользователя и не заполнить одно из обязательных полей (name)")
    @Test
    public void  createUserNoName() {
        User userNoName = User.random();
        userNoName.setName("");
        ValidatableResponse response = userApi.createUser(userNoName);
        userChk.createUserWithoutField(response);
    }

    @DisplayName("создать пользователя и не заполнить одно из обязательных полей (password)")
    @Test
    public void createUserNoPass() {
        User userNoPass = User.random();
        userNoPass.setPassword("");
        ValidatableResponse response = userApi.createUser(userNoPass);
        userChk.createUserWithoutField(response);
    }
}