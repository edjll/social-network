package ru.edjll.backend.dto.user.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.edjll.backend.entity.UserPost;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDtoForUpdate {

    @Length(min = 1, max = 150)
    private String text;

    public UserPost toPost() {
        UserPost userPost = new UserPost();

        userPost.setText(text);
        userPost.setModifiedDate(LocalDateTime.now());

        return userPost;
    }
}
