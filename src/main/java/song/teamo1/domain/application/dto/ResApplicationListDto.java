package song.teamo1.domain.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import song.teamo1.domain.team.entity.Application;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResApplicationListDto {
    private Long applicationId;
    private String title;
    private String content;
    private Long userId;
    private String username;

    public ResApplicationListDto(Application application) {
        this.applicationId = application.getId();
        this.title = application.getTitle();
        this.content = application.getContent();
        this.userId = application.getUser().getId();
        this.username = application.getUser().getUsername();
    }
}
