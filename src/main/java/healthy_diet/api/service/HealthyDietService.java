package healthy_diet.api.service;

import healthy_diet.api.config.DateConfig;
import healthy_diet.api.model.HealthyDiet;
import healthy_diet.api.model.Recipe;
import healthy_diet.api.model.User;
import healthy_diet.api.repository.HealthyDietRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class HealthyDietService {

    private final HealthyDietRepository healthyDietRepository;
    private final UserService userService;
    private final RecipeService recipeService;

    public HealthyDietService(HealthyDietRepository healthyDietRepository, UserService userService, RecipeService recipeService) {
        this.healthyDietRepository = healthyDietRepository;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @Transactional
    public void addToHealthyToday(Long id, String username) {
        User user = this.userService.loadUserByUsername(username);
        Recipe recipe = this.recipeService.getRecipe(id);

        Optional<HealthyDiet> healthyDiet = this.healthyDietRepository.findFirstByRecipeIdAndUsername(recipe, user);
        if (healthyDiet.isEmpty()) {
            this.healthyDietRepository.save(new HealthyDiet(user, recipe, DateConfig.getDateNow()));
        }
    }

    @Transactional
    public void removeFromHealthyToday(Long id, String username) {
        User user = this.userService.loadUserByUsername(username);
        Recipe recipe = this.recipeService.getRecipe(id);
        HealthyDiet healthyDiet = this.healthyDietRepository.findFirstByRecipeIdAndUsername(recipe, user)
                .orElseThrow();
        this.healthyDietRepository.delete(healthyDiet);

    }
}
