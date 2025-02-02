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
    private TeamRole teamRole;

    public static TeamMember create(Team team, User user) {
        return new TeamMember(team, user);
    }

    public Boolean isTeamLeader(User user) {
        if (this.user.getId().equals(user.getId()) && (teamRole == TeamRole.LEADER || teamRole == TeamRole.SUB_LEADER)) {
            return true;
        }

        return false;
    }

    public void grantRole(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    public enum TeamRole {
        MEMBER, LEADER, SUB_LEADER
    }

    private TeamMember(Team team, User user) {
        this.team = team;
        this.user = user;
        this.teamRole = TeamRole.MEMBER;
    }
}
