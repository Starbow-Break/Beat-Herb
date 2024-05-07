package store.beatherb.restapi.content.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.content.domain.HashTag;

@Data
@Builder
public class UpdateHashTagResponse {
    @NotNull(message = "hashTagId는 null 값이 들어오면 안됩니다.")
    private Long id;
    private String name;

    public static UpdateHashTagResponse toDto(HashTag hashTag){
        return UpdateHashTagResponse.builder()
                .id(hashTag.getId())
                .name(hashTag.getName())
                .build();
    }
}
