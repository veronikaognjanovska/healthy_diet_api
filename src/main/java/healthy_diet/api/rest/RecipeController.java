package healthy_diet.api.rest;

import healthy_diet.api.model.Recipe;
import healthy_diet.api.model.dto.RecipeDto;
import healthy_diet.api.model.dto.SearchRecipeDto;
import healthy_diet.api.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    private List<Recipe> getRecipes() {
        return this.recipeService.getRecipes();
    }

    @GetMapping("/{id}")
    private Recipe getRecipe(@PathVariable Long id) {
        return this.recipeService.getRecipe(id);
    }

    @PutMapping("/search")
    private List<Recipe> searchRecipes(@RequestBody SearchRecipeDto searchRecipeDto) {
        return this.recipeService.searchRecipes(searchRecipeDto);
    }

    @PostMapping("/add")
    public ResponseEntity<Recipe> saveRecipe(@RequestBody RecipeDto recipeDto) {
        return this.recipeService.saveRecipe(recipeDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        return this.recipeService.editRecipe(id, recipeDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/save/{username}/{id}")
    private ResponseEntity<?> saveRecipes(@PathVariable Long id, @PathVariable String username) {
        try {
            this.recipeService.save(id, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/unsave/{username}/{id}")
    private ResponseEntity<?> unsaveRecipes(@PathVariable Long id, @PathVariable String username) {
        try {
            this.recipeService.unsave(id, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteRecipes(@PathVariable Long id) {
        try {
            this.recipeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
