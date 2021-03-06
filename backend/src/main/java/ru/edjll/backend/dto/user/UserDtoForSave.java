package ru.edjll.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoForSave {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private List<@Valid CredentialDtoForSave> credentials;
}
