package store.beatherb.restapi.content.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.content.domain.HashTag;

@Data
@Builder
public class RegistHashTagResponse {
    @NotNull(message = "hashTagId는 null 값이 들어오면 안됩니다.")
    private Long id;
    private String name;

    public static RegistHashTagResponse toDto(HashTag hashTag){
        return RegistHashTagResponse.builder()
                .id(hashTag.getId())
                .name(hashTag.getName())
                .build();
    }
}
