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
    private Teaming teaming;

    private ProjectPost(User user, Teaming teaming, String title, String content) {
        super(user, title, content);
        this.teaming = teaming;
    }

    public static ProjectPost create(User user, Teaming teaming, String title, String content) {
        return new ProjectPost(user, teaming, title, content);
    }

    protected ProjectPost() {
        super(null, null, null);
    }
}
