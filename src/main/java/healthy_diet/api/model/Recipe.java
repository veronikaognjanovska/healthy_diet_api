package healthy_diet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler",
        "healthyDietUsername",
        "savedBy"
})
public class Recipe {

    @OneToMany(mappedBy = "recipeId", fetch = FetchType.LAZY)
    Set<HealthyDiet> healthyDietUsername;
    @ManyToMany(mappedBy = "saved")
    Set<User> savedBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "timeToPrepare")
    private Integer timeToPrepare;
    @Column(name = "people")
    private Integer people;
    @Column(name = "rate")
    private Double rate;
    @Column(name = "types")
    private String types;
    @Column(name = "calories")
    private Double calories;
    @Column(name = "by")
    private String by;
    @Column(name = "datetime")
    private String dateTime;
    @Column(name = "preparation")
    private String preparation;
    @Column(name = "ingredients")
    private String ingredients;

    public Recipe() {
    }

    public Recipe(String title, Integer timeToPrepare, Integer people, Double rate, List<String> types,
                  Double calories, String by, String dateTime, String preparation, List<String> ingredients) {
        this.id = id;
        this.title = title;
        this.timeToPrepare = timeToPrepare;
        this.people = people;
        this.rate = rate;
        this.types = listToString(types);
        this.by = by;
        this.calories = calories;
        this.dateTime = dateTime;
        this.preparation = preparation;
        this.ingredients = listToString(ingredients);
        this.healthyDietUsername = new HashSet<>();
    }

    private String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String el : list) {
            sb.append(el);
            sb.append(",");
        }
        return sb.toString();
    }

    private List<String> stringToList(String str) {
        String[] list = str.split(",");
        return Arrays.asList(list.clone());
    }

    public List<String> getTypes1() {
        return stringToList(this.types);
    }

    public void setTypes1(List<String> list) {
        this.types = listToString(list);
    }

    public List<String> getIngredients1() {
        return stringToList(this.ingredients);
    }

    public void setIngredients1(List<String> list) {
        this.ingredients = listToString(list);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timeToPrepare=" + timeToPrepare +
                ", people=" + people +
                ", rate=" + rate +
                ", types='" + types + '\'' +
                ", calories=" + calories +
                ", by='" + by + '\'' +
                ", dateTime=" + dateTime +
                ", preparation='" + preparation + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}