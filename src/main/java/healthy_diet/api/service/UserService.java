package healthy_diet.api.service;

import healthy_diet.api.config.DateConfig;
import healthy_diet.api.model.Role;
import healthy_diet.api.model.User;
import healthy_diet.api.model.dto.UserDto;
import healthy_diet.api.model.exceptions.NotFound;
import healthy_diet.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByUsername(String username) throws NotFound {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFound(username));
    }

    public User loadUserByUsername1(String username) throws NotFound {
        return this.userRepository.findByUsername1(username)
                .orElseThrow(() -> new NotFound(username));
    }

    @Transactional
    public Optional<User> save(UserDto dto) {
        if (dto.getUsername() == null || dto.getPassword() == null || dto.getNameSurname() == null ||
                dto.getEmail() == null || dto.getBirthday() == null)
            throw new NotFound("");
        if (this.userRepository.findByUsername1(dto.getUsername()).isPresent()) {
            throw new NotFound(dto.getUsername());
        }
        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()),
                dto.getEmail(), dto.getNameSurname(), DateConfig.getZonedDateTimeFromDateStringDateDate(dto.getBirthday()),
                Role.ROLE_USER);
        this.userRepository.save(user);

        return Optional.of(this.userRepository.save(user));
    }

}
