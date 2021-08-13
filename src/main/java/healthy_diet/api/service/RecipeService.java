package healthy_diet.api.service;

import healthy_diet.api.config.DateConfig;
import healthy_diet.api.model.Recipe;
import healthy_diet.api.model.User;
import healthy_diet.api.model.dto.RecipeDto;
import healthy_diet.api.model.dto.SearchRecipeDto;
import healthy_diet.api.model.exceptions.NotFound;
import healthy_diet.api.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;

    public RecipeService(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public Recipe getRecipe(Long id) {
        return this.recipeRepository.findById(id).orElseThrow(() -> new NotFound(String.format("Recipe with id: %d not found!", id)));
    }

    public Boolean existsRecipe(Long id) {
        return this.recipeRepository.existsById(id);
    }

    public List<Recipe> getRecipes() {
        return this.recipeRepository.findAll();
    }

    public List<Recipe> searchRecipes(SearchRecipeDto searchRecipeDto) {
        List<Recipe> recipeList = new LinkedList<>();
        for (Recipe r : this.getRecipes()) {
            if (!searchRecipeDto.getSearchInput().equals("") &&
                    !r.getTitle().toLowerCase().contains(searchRecipeDto.getSearchInput().toLowerCase())) {
                continue;
            }
            if (!r.getTypes1().containsAll(searchRecipeDto.getList())) {
                continue;
            }
            recipeList.add(r);
        }
        return recipeList;
    }

    @Transactional
    public Optional<Recipe> saveRecipe(RecipeDto recipeDto)
            throws IllegalArgumentException, NotFound {
        this.checkParameter(recipeDto);
        Recipe recipe = new Recipe(recipeDto.getTitle(), recipeDto.getTimeToPrepare(), recipeDto.getPeople(),
                0.0, recipeDto.getTypes(), recipeDto.getCalories(), recipeDto.getBy(), DateConfig.getDateNow(),
                recipeDto.getPreparation(), recipeDto.getIngredients());
        return Optional.of(this.recipeRepository.save(recipe));
    }

    @Transactional
    public Optional<Recipe> editRecipe(Long recipe_id, RecipeDto recipeDto) throws NotFound {
        Recipe recipe = this.getRecipe(recipe_id);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setTimeToPrepare(recipeDto.getTimeToPrepare());
        recipe.setPeople(recipeDto.getPeople());
        recipe.setTypes1(recipeDto.getTypes());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setPreparation(recipeDto.getPreparation());
        recipe.setIngredients1(recipeDto.getIngredients());
        return Optional.of(this.recipeRepository.save(recipe));
    }


    @Transactional
    public void save(Long recipe_id, String username) throws NotFound {
//        Recipe recipe = this.getRecipe(recipe_id);
//        User user = this.userService.loadUserByUsername(username);
//        recipe.getSavedBy().add(user);
//        this.recipeRepository.save(recipe);
    }

    @Transactional
    public void unsave(Long recipe_id, String username) throws NotFound {
//        Recipe recipe = this.getRecipe(recipe_id);
//        User user = this.userService.loadUserByUsername(username);
//        recipe.getSavedBy().remove(user);
//        this.recipeRepository.save(recipe);
    }

    @Transactional
    public void delete(Long recipe_id) throws NotFound {
        Recipe recipe = this.getRecipe(recipe_id);
       this.recipeRepository.delete(recipe);
    }

    private void checkParameter(RecipeDto recipeDto) throws IllegalArgumentException {
        if (recipeDto.getTitle() == null || recipeDto.getTitle().isEmpty() ||
                recipeDto.getTimeToPrepare() == null || recipeDto.getTimeToPrepare() <= 0 ||
                recipeDto.getPeople() == null || recipeDto.getPeople() <= 0 ||
                recipeDto.getTypes() == null ||
                recipeDto.getBy() == null || recipeDto.getBy().isEmpty() ||
                recipeDto.getCalories() == null || recipeDto.getCalories() < 0 ||
                recipeDto.getPreparation() == null || recipeDto.getPreparation().isEmpty() ||
                recipeDto.getIngredients() == null
        ) {
            throw new NotFound("Error checking parameters");
        }
    }
}
