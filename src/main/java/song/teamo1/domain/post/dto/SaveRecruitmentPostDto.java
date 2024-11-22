package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.post.entity.RecruitmentPost;
import song.teamo1.domain.user.entity.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveRecruitmentPostDto {
    private String title;
    private String content;

    public RecruitmentPost toRecruitmentPost(User user) {
        return RecruitmentPost.create(user, title, content);
    }
}
