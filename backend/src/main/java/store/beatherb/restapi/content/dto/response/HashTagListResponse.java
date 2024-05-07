package store.beatherb.restapi.content.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.content.domain.HashTag;

@Getter
public class HashTagListResponse {

    Long id;
    String name;

    @Builder
    public HashTagListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HashTagListResponse toDto(HashTag entity){
        return HashTagListResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
