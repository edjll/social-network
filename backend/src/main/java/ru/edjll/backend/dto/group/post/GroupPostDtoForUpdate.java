package ru.edjll.backend.dto.group.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.edjll.backend.entity.GroupPost;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPostDtoForUpdate {

    @Length(min = 1, max = 100)
    private String text;

    public GroupPost toGroupPost() {
        GroupPost groupPost = new GroupPost();

        groupPost.setText(text);

        return groupPost;
    }
}
