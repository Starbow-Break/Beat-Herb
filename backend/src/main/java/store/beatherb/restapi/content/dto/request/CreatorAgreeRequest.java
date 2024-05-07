package store.beatherb.restapi.content.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatorAgreeRequest {
    @NotNull(message = "id 는 null 이 아니여야 합니다")
    Long id;
    @NotNull(message = "동의 여부는 null 이 아니여야 합니다.")
    Boolean agree;
}
