package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.Model;

import static io.restassured.RestAssured.given;
import static praktikum.Const.BASE_URI;
import static praktikum.Const.ORDER;



public class UserOrder extends Model {

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderAuth(Order order, String accessToken){
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderNoAuth(Order order){
        return spec()
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получение заказов, авторизованный пользователь")
    public ValidatableResponse getOrderAuth(String accesToken){
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accesToken)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER)
                .then()
                .log()
                .all();

    }

    @Step("Получение заказов, неавторизованный пользователь")
    public ValidatableResponse getOrderNoAuth(){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER)
                .then()
                .log()
                .all();
    }

}
