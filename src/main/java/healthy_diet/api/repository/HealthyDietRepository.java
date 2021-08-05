package healthy_diet.api.repository;

import healthy_diet.api.model.HealthyDiet;
import healthy_diet.api.model.Recipe;
import healthy_diet.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthyDietRepository extends JpaRepository<HealthyDiet, Long> {
    Optional<HealthyDiet> findFirstByRecipeIdAndUsername(Recipe recipe, User username);

}