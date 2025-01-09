package song.teamo1.domain.team.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo1.domain.common.entity.CommonEntity;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public boolean isWriter(User user) {
        return this.user.getId().equals(user.getId());
    }

    public void accept() {
        this.status = Status.APPROVED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public static Application create(String title, String content, User user, Team team) {
        return new Application(title, content, user, team);
    }

    private Application(String title, String content, User user, Team team) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.team = team;
        this.status = Status.PENDING;
    }
}
