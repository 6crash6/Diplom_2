package test;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.ValidatableResponse;
import order.OrderChk;
import order.UserOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import user.User;
import user.UserApi;
import user.UserChk;

@ExtendWith(AllureJunit5.class)
public class TestGetOrders {
    private String accessToken;
    private UserOrder userOrder;
    private UserApi userApi;
    private OrderChk orderChk;
    private UserChk userChk;
    private User user;

    @BeforeEach
    public void setUp(){
        userOrder = new UserOrder();
        userApi = new UserApi();
        userChk = new UserChk();
        orderChk = new OrderChk();

        user = User.random();
        ValidatableResponse createResponse = userApi.createUser(user);
        userChk.createUserOk(createResponse);
        accessToken = userApi.getToken(createResponse);
    }

    @AfterEach
    public void cleanUp(){
        if (accessToken != null){
            userApi.deleteUser(accessToken);
        }
    }

    @DisplayName("Получение заказов с авторизацией")
    @Test
    public void getAuthOrder() {
        ValidatableResponse response = userOrder.getOrderAuth(accessToken);
        orderChk.getOrdersAuth(response);
    }

    @DisplayName("Получение заказов без авторизации")
    @Test
    public void getNotAuthOrder(){
        ValidatableResponse response = userOrder.getOrderNoAuth();
        orderChk.getOrdersNoAuth(response);
    }
}
