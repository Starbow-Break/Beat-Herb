package store.beatherb.restapi.live.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiveContentResponse {
    private Long id;
    private String title;
}
