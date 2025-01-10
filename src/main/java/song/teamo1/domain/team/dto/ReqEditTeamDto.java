package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqEditTeamDto {
    private Long teamId;
    private String teamName;
    private String teamInfo;
}
