package healthy_diet.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private String title;
    private Integer timeToPrepare;
    private Integer people;
    private List<String> types;
    private Double calories;
    private String by;
    private String preparation;
    private List<String> ingredients;

}
