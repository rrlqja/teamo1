package song.teamo1.domain.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
public class ProjectPost extends Post {
    @OneToOne
    @JoinColumn(name = "recruitment_id")
    private RecruitmentPost recruitmentPost;

    private ProjectPost(User user,  RecruitmentPost recruitmentPost, String title, String content) {
        super(user, title, content);
        this.recruitmentPost = recruitmentPost;
    }

    public static ProjectPost create(User user, RecruitmentPost recruitmentPost, String title, String content) {
        return new ProjectPost(user, recruitmentPost, title, content);
    }

    protected ProjectPost() {
        super(null, null, null);
    }
}
