package song.teamo1.domain.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
public class CommonPost extends Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CommonPost(User user, String title, String content) {
        super(user, title, content);
    }

    public static CommonPost create(User user, String title, String content) {
        return new CommonPost(user, title, content);
    }

    protected CommonPost() {
        super(null, null, null);
    }
}
