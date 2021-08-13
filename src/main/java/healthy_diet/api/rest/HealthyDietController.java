package healthy_diet.api.rest;

import healthy_diet.api.model.HealthyDiet;
import healthy_diet.api.service.HealthyDietService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/healthy")
public class HealthyDietController {
    private final HealthyDietService healthyDietService;

    public HealthyDietController(HealthyDietService healthyDietService) {
        this.healthyDietService = healthyDietService;
    }

    @GetMapping("/{username}")
    private List<HealthyDiet> get(@PathVariable String username) {
        return this.healthyDietService.getHealthy(username);
    }

    @GetMapping("/{username}/{date}")
    private List<HealthyDiet> getDate(@PathVariable String username, @PathVariable String date) {
        return this.healthyDietService.getHealthyDate(username, date);
    }

    @GetMapping("/add/{username}/{id}")
    private ResponseEntity<?> addToHealthyToday(@PathVariable Long id, @PathVariable String username) {
        try {
            this.healthyDietService.addToHealthyToday(id, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/remove/{username}/{id}")
    private ResponseEntity<?> removeFromHealthyToday(@PathVariable Long id, @PathVariable String username) {
        try {
            this.healthyDietService.removeFromHealthyToday(id, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
