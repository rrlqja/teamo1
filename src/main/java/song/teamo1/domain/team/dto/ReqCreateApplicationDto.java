package song.teamo1.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.team.entity.Application;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.user.entity.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqCreateApplicationDto {
    private String title;
    private String content;

    public Application toEntity(User user, Team team) {
        return Application.create(title, content, user, team);
    }
}
