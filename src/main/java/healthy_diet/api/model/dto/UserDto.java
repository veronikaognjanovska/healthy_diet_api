package healthy_diet.api.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;

    private String password;

    private String nameSurname;

    private String email;

    private String birthday;

    public UserDto(String username, String password, String nameSurname, String email, String birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.nameSurname = nameSurname;
    }
}