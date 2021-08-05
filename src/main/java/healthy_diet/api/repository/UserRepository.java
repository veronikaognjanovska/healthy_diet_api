package healthy_diet.api.repository;

import healthy_diet.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value="SELECT b.username, b.email,b.nameSurname,b.username,b.birthday FROM users b WHERE b.username = ?1 ",
            nativeQuery = true)
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}