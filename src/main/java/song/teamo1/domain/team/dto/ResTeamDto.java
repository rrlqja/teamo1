package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.team.entity.Team;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResTeamDto {
    private Long id;
    private String name;

    public ResTeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
