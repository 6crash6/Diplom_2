package ingredients;

import lombok.Data;

import java.util.List;

@Data
public class OrderIngredients {
    private String success;
    private List<Ingredients> data;
}