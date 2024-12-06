package song.teamo1.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
public class Teaming extends Post {
    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    @Enumerated(EnumType.STRING)
    private Status status;


    private Teaming(User user, String title, String content, Team team, Status status) {
        super(user, title, content);
        this.team = team;
        this.status = status;
    }

    public static Teaming create(User user, String title, String content, Team team) {
        return new Teaming(user, title, content, team, Status.RECRUITING);
    }

    public enum Status {
        RECRUITING, COMPLETED, CANCELLED;
    }

    protected Teaming() {
        super(null, null, null);
    }
}
