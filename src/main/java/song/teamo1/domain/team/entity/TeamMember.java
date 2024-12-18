package song.teamo1.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo1.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "user_id"})
})
public class TeamMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private TEAM_ROLE teamRole;

    public static TeamMember create(Team team, User user, TEAM_ROLE teamRole) {
        if (teamRole == null) {
            return new TeamMember(team, user);
        }
        return new TeamMember(team, user, teamRole);
    }

    public Boolean isTeamLeader(User user) {
        if (teamRole == TEAM_ROLE.LEADER || teamRole == TEAM_ROLE.SUB_LEADER) {
            return true;
        }

        return false;
    }

    public enum TEAM_ROLE {
        MEMBER, LEADER, SUB_LEADER
    }

    private TeamMember(Team team, User user) {
        this.team = team;
        this.user = user;
        this.teamRole = TEAM_ROLE.MEMBER;
    }

    private TeamMember(Team team, User user, TEAM_ROLE teamRole) {
        this.team = team;
        this.user = user;
        this.teamRole = teamRole;
    }
}
