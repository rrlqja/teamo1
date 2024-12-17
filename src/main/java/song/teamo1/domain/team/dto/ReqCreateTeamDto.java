package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.team.entity.Team;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqCreateTeamDto {
    private String name;
    private String info;

    public Team toEntity() {
        return Team.create(name, info);
    }
}
