package ru.edjll.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoForAdminPage {

    private String id;
    private String username;
    private String email;
    private String city;
    private boolean enabled;
}
