package song.teamo1.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo1.domain.common.entity.CommonEntity;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long views;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    public Post(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0L;
    }
}
