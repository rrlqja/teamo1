package song.teamo1.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo1.domain.common.entity.CommonEntity;
import song.teamo1.domain.post.entity.consts.PostStatus;
import song.teamo1.domain.post.entity.consts.PostType;
import song.teamo1.domain.user.entity.User;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    @Enumerated(STRING)
    private PostType type;
    @Enumerated(STRING)
    private PostStatus status;
    private Integer views;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToOne
    @JoinColumn(name = "recruitment_id")
    private Post recruitmentPost;

    private Post(String title, String content, PostType type, PostStatus status, User writer, Post recruitmentPost) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.views = 0;
        this.writer = writer;
        this.recruitmentPost = recruitmentPost;
    }

    public static Post createRecruitment(String title, String content, User writer) {
        return new Post(title, content, PostType.RECRUITMENT, PostStatus.RECRUITING, writer, null);
    }

    public static Post createProject(String title, String content, Post recruitmentPost, User writer) {
        return new Post(title, content, PostType.PROJECT, null, writer, recruitmentPost);
    }

    public static Post createCommon(String title, String content, User writer) {
        return new Post(title, content, null, null, writer, null);
    }

}
