package song.teamo1.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
public class RecruitmentPost extends Post {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;
//    @OneToOne(mappedBy = "project_id")
//    private ProjectPost projectPost;

    private RecruitmentPost(User user, Status status, String title, String content) {
        super(user, title, content);
        this.status = status;
    }

    public static RecruitmentPost create(User user, String title, String content) {
        return new RecruitmentPost(user, Status.RECRUITING, title, content);
    }

    public enum Status {
        RECRUITING, COMPLETED, CANCELLED;
    }

    protected RecruitmentPost() {
        super(null, null, null);
    }
}
