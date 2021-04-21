package ru.edjll.backend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.edjll.backend.entity.Group;
import ru.edjll.backend.validation.exists.Exists;
import ru.edjll.backend.validation.unique.Unique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoForUpdate {

    @Length(min = 5, max = 40)
    private String title;

    @Length(min = 10, max = 150)
    private String description;

    @Length(min = 3, max = 15)
    @Pattern(regexp = "^\\w+$")
    @Unique(table = "groups", column = "address")
    private String address;

    public Group toGroup() {
        Group group = new Group();

        group.setTitle(title);
        group.setDescription(description);
        group.setAddress(address);

        return group;
    }
}
