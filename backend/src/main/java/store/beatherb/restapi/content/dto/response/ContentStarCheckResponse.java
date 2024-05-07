package store.beatherb.restapi.content.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.content.domain.HashTag;

@Data
@Builder
public class ContentStarCheckResponse {
    Long id;
    boolean star;

    public ContentStarCheckResponse(Long id, boolean star) {
        this.id = id;
        this.star = star;
    }
}
