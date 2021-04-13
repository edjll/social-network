package ru.edjll.backend.dto.user.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDtoForSearch {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private Integer status;
    private String friendId;
}
