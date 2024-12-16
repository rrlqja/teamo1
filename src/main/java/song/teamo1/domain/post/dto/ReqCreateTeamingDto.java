package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.user.entity.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqCreateTeamingDto {
    private String title;
    private String content;
    private Long teamId;

    public Teaming toEntity(User user, Team team) {
        return Teaming.create(user, title, content, team);
    }
}
