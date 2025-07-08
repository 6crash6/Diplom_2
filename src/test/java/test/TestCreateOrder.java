package test;

import ingredients.IngredientsApi;
import ingredients.OrderIngredients;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.ValidatableResponse;
import order.Order;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(AllureJunit5.class)
public class TestCreateOrder {
    private String accessToken;
    private UserOrder userOrder;
    private UserApi userApi;
    private OrderChk orderChk;
    private UserChk userChk;
    private User user;
    private IngredientsApi ingredientsApi;
    private List<String> ingredientId;
    private Order order;

    @BeforeEach
    public void setUp(){
        userOrder = new UserOrder();
        userApi = new UserApi();
        orderChk = new OrderChk();
        userChk = new UserChk();
        ingredientsApi = new IngredientsApi();

        user = User.random();
        ValidatableResponse createResponse = userApi.createUser(user);
        userChk.createUserOk(createResponse);
        accessToken = userApi.getToken(createResponse);
        ingredientId = new ArrayList<>();
        order = new Order(ingredientId);
    }

    @AfterEach
    public void cleanUp(){
        if (accessToken != null){
            userApi.deleteUser(accessToken);
        }
    }

    @DisplayName("Создание заказа с ингредиентами с авторизацией")
    @Test
    public void createOrderNormIngrdts(){
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientId.add(orderIngredients.getData().get(0).get_id());
        ingredientId.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse response = userOrder.createOrderAuth(order, accessToken);
        orderChk.getOrdersAuth(response);
    }

    @DisplayName("Создание заказа с ингредиентами без авторизации")
    @Test
    public void createOrderNormIngrdtsNoAuth(){
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientId.add(orderIngredients.getData().get(0).get_id());
        ingredientId.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse response = userOrder.createOrderNoAuth(order);
        orderChk.getOrdersAuth(response);
    }

    @DisplayName("Создание заказа без ингредиентов с авторизацией")
    @Test
    public void createOrderNoIngrdtsAuth(){
        ValidatableResponse response = userOrder.createOrderAuth(order,accessToken);
        orderChk.createOrderNoIngredients(response);
    }

    @DisplayName("Создание заказа без ингредиентов без авторизации")
    @Test
    public void createOrderNoIngrdtsNoAuth(){
        ValidatableResponse response = userOrder.createOrderNoAuth(order);
        orderChk.createOrderNoIngredients(response);
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов с авторизацией")
    @Test
    public void createOrderWithBadHashIngrdts(){
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientId.add(orderIngredients.getData().get(0).get_id()+"99999");
        ingredientId.add(orderIngredients.getData().get(1).get_id()+"99999");
        ValidatableResponse response = userOrder.createOrderAuth(order,accessToken);
        orderChk.createOrderBadHash(response);
    }


    @DisplayName("Создание заказа с неверным хешем ингредиентов без авторизации")
    @Test
    public void createOrderBadHashIngrdtsNoAuth(){
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientId.add(orderIngredients.getData().get(0).get_id()+"99999");
        ingredientId.add(orderIngredients.getData().get(1).get_id()+"99999");
        ValidatableResponse response = userOrder.createOrderNoAuth(order);
        orderChk.createOrderBadHash(response);
    }
}
