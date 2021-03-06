package ru.edjll.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edjll.backend.dto.group.GroupDtoForSearch;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupsDto {

    private List<GroupDtoForSearch> groups;
    private int count;
}
