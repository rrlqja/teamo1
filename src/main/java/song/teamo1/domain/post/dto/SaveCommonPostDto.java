package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.post.entity.CommonPost;
import song.teamo1.domain.user.entity.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveCommonPostDto {
    private String title;
    private String content;

    public CommonPost toCommonPost(User user) {
        return CommonPost.create(user, title, content);
    }
}
