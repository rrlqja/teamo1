package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo1.domain.team.entity.TeamMember;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResGetTeamListTeamMemberDto {
    private String username;

    public ResGetTeamListTeamMemberDto(TeamMember teamMember) {
        this.username = teamMember.getUser().getUsername();
    }
}
