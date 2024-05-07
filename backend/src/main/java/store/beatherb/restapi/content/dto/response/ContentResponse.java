package store.beatherb.restapi.content.dto.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class ContentResponse {
    private Long id;
    private String title;
    private String name;
    private String image;

    @Builder
    public ContentResponse(Long id, String title, String name){
        this.id = id;
        this.title = title;
        this.name = name;
        this.image = "/api/content/image/" + id;
    }
}
