package healthy_diet.api.service;

import healthy_diet.api.config.DateConfig;
import healthy_diet.api.model.HealthyDiet;
import healthy_diet.api.model.Recipe;
import healthy_diet.api.model.User;
import healthy_diet.api.repository.HealthyDietRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        Recipe recipe = this.recipeService.getRecipe(id);
        User user = this.userService.loadUserByUsername(username);
        Optional<HealthyDiet> healthyDiet = this.healthyDietRepository.findFirstByRecipeIdAndUsernameAndDate(recipe, user, DateConfig.getDateNowString());
        if (healthyDiet.isEmpty()) {
            this.healthyDietRepository.save(new HealthyDiet(user, recipe, DateConfig.getDateNowString()));
        }
    }

    @Transactional
    public void removeFromHealthyToday(Long id, String username) {
        User user = this.userService.loadUserByUsername(username);
        Recipe recipe = this.recipeService.getRecipe(id);
        HealthyDiet healthyDiet = this.healthyDietRepository.findFirstByRecipeIdAndUsernameAndDate(recipe, user, DateConfig.getDateNowString())
                .orElseThrow();
        this.healthyDietRepository.delete(healthyDiet);

    }


    @Transactional
    public List<HealthyDiet> getHealthy(String username) {
        User user = this.userService.loadUserByUsername(username);
        return this.healthyDietRepository.findAllByUsernameAndDateLike(user, DateConfig.getDateNowString());
    }

    @Transactional
    public List<HealthyDiet> getHealthyDate(String username, String date) {
        User user = this.userService.loadUserByUsername(username);
        return this.healthyDietRepository.findAllByUsernameAndDateLike(user, date);
    }

}
