package hyfz.hyfz.PlayLoad.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    @Size(min = 3, max = 20, message = "firstName should be more than 3")
    private String first_name;

    @NotNull
    @Size(min = 3, max = 20, message = "lastName should be more than 3")
    private String last_name;

    @Size(min = 3, max = 20, message = "username should be more than 3")
    private String username;

    @Email(message = "email incorrect")
    private String email;

    @Size(min = 6, max = 40, message = "Password should not be empty and more than 6 character")
    private String password;

}
