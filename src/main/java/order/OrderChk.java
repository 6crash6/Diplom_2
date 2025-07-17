package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChk {

    @Step("Заказ создан успешно")
    public void createOrder(ValidatableResponse createOrderResponse) {
        createOrderResponse
                .body("success", equalTo(true))
                .body("order", notNullValue())
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Заказ без ингредиентов не создать")
    public void createOrderNoIngredients(ValidatableResponse orderResponse) {
        orderResponse
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Заказ с неверным хешем ингредиентов не создать")
    public void createOrderBadHash(ValidatableResponse orderResponse) {
        orderResponse
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Получение заказов")
    public void getOrdersAuth(ValidatableResponse okResponse) {
        okResponse
                .body("success", equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Получение заказов неавторизованный пользователь")
    public void getOrdersNoAuth(ValidatableResponse nokResponse) {
        nokResponse
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}