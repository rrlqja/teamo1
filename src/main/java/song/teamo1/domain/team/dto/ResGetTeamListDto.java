package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResGetTeamListDto {
    private Long teamId;
    private String teamName;
    private String teamInfo;
    private String leaderName;

    public ResGetTeamListDto(Team team, TeamMember teamMember) {
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.teamInfo = team.getInfo();
        this.leaderName = teamMember.getUser().getName();
    }
}
