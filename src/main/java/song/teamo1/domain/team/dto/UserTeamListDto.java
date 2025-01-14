package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.team.entity.Team;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamListDto {
    private Long teamId;
    private String teamName;
    private String teamInfo;

    public UserTeamListDto(Team team) {
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.teamInfo = team.getInfo();
    }
}
