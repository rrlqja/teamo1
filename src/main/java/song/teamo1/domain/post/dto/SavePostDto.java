package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.post.entity.CommonPost;
import song.teamo1.domain.post.entity.ProjectPost;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.user.entity.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavePostDto {
    private String title;
    private String content;
    private Long recruitmentId;

    public CommonPost toCommonPost(User user) {
        return CommonPost.create(user, title, content);
    }

    public Teaming toRecruitmentPost(User user) {
        return Teaming.create(user, title, content);
    }

    public ProjectPost toProjectPost(User user, Teaming teaming) {
        return ProjectPost.create(user, teaming, title, content);
    }
}
