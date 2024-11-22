package song.teamo1.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProjectPostDto {
    private String title;
    private String content;
}
