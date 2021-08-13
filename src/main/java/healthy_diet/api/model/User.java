package healthy_diet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Table(name = "`users`")
@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler",
        "password",
        "healthyDietUsername",
        "saved",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User implements UserDetails {

    @OneToMany(mappedBy = "username",fetch = FetchType.LAZY)
    Set<HealthyDiet> healthyDietUsername;

    @Id
    private String username;

    public User(String username, String password, String email, String nameSurname, ZonedDateTime birthday, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nameSurname = nameSurname;
        this.birthday = birthday;
        this.role = role;
    }

//    @Override
//    public String toString() {
//        return "User{";
//    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nameSurname='" + nameSurname + '\'' +
                ", birthday=" + birthday +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                ", role=" + role +
                '}';
    }

    private String password;
    private String email;
    private String nameSurname;
    private ZonedDateTime birthday;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "saved",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    Set<Recipe> saved;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}