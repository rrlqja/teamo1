package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.teamo1.domain.post.entity.Teaming;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamingListDto {
    private Long id;
    private String title;
    private String writer;

    public TeamingListDto(Teaming teaming) {
        this.id = teaming.getId();
        this.title = teaming.getTitle();
        this.writer = teaming.getWriter().getName();
    }
}
