package ru.edjll.backend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoForSearch {

    private Long id;
    private String address;
    private String title;
    private String description;
    private Boolean subscribed;
}
