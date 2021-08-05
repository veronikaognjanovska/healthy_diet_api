package healthy_diet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class HealthyDiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"healthyDietUsername", "username",
            "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "username")
    User username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"healthyDietUsername", "recipeId",
            "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "recipe_id")
    Recipe recipeId;

    ZonedDateTime date;

    public HealthyDiet(User username, Recipe recipeId, ZonedDateTime date) {
        this.username = username;
        this.recipeId = recipeId;
        this.date = date;
    }
}
