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
public class TestLogin {
    private String randomEmail;
    private String randomPassword;
    private String accessToken;
    User user;
    private final UserApi userApi = new UserApi();
    UserChk userChk = new UserChk();

    @BeforeEach
    public void setUp(){
        String randomUserMail = RandomStringUtils.randomAlphabetic(5);
        String randomDomainMail = RandomStringUtils.randomAlphabetic(5) + ".com";
        randomEmail = randomUserMail + "@" + randomDomainMail;
        randomPassword = RandomStringUtils.randomAlphanumeric(8);
        user = User.random();
        ValidatableResponse response = userApi.createUser(user);
        userChk.createUserOk(response);
        accessToken = userApi.getToken(response);
    }

    @AfterEach
    public void cleanUp(){
        if (accessToken != null){
            userApi.deleteUser(accessToken);
        }
    }

    @DisplayName("Логин")
    @Test
    public void logIn(){
        var login = UserCreds.from(user);
        ValidatableResponse response = userApi.loginUser(login);
        userChk.loginUserOk(response);
    }

    @DisplayName("Логин без e-mail")
    @Test
    public void loginNoEmail(){
        user.setEmail("");
        var login = UserCreds.from(user);
        ValidatableResponse response = userApi.loginUser(login);
        userChk.loginWrongCreditinals(response);
    }

    @DisplayName("Логин без password")
    @Test
    public void loginNoPass(){
        user.setPassword("");
        var login = UserCreds.from(user);
        ValidatableResponse response = userApi.loginUser(login);
        userChk.loginWrongCreditinals(response);
    }

    @DisplayName("Логин неверный e-mail")
    @Test
    public void loginWrongMail(){
        user.setEmail(randomEmail);
        var login = UserCreds.from(user);
        ValidatableResponse response = userApi.loginUser(login);
        userChk.loginWrongCreditinals(response);
    }

    @DisplayName("Логин неверный password")
    @Test
    public void loginWrongPass(){
        user.setPassword(randomPassword);
        var login = UserCreds.from(user);
        ValidatableResponse response = userApi.loginUser(login);
        userChk.loginWrongCreditinals(response);
    }
}
