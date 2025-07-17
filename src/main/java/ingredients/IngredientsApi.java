package ingredients;

import io.qameta.allure.Step;
import praktikum.Const;
import praktikum.Model;

public class IngredientsApi extends Model {

    @Step("Получение списка ингредиентов")
    public OrderIngredients getIngredients() {

        return spec()
                .get(Const.INGREDIENTS)
                .then()
                .log().all()
                .extract()
                .body()
                .as(OrderIngredients.class);
    }
}