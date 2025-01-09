package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResGetTeamDto {
    private Long teamId;
    private String teamName;
    private String teamInfo;
    private boolean isAdmin;
    private List<ResGetTeamMemberDto> teamMemberList = new ArrayList<>();

    public ResGetTeamDto(Team team, List<TeamMember> teamMemberList, boolean isAdmin) {
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.teamInfo = team.getInfo();
        this.isAdmin = isAdmin;
        this.teamMemberList = teamMemberList.stream()
                .map(ResGetTeamMemberDto::new)
                .toList();
    }

    @ToString
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResGetTeamMemberDto {
        private String name;

        public ResGetTeamMemberDto(TeamMember teamMember) {
            this.name = teamMember.getUser().getName();
        }
    }
}
