package store.beatherb.restapi.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.content.domain.HashTag;

@Data
@AllArgsConstructor
@Builder
public class HashTagDTO {
    private Long id;
    private String name;

    public static HashTagDTO toDto(HashTag entity) {
        return HashTagDTO.builder()
              .id(entity.getId())
              .name(entity.getName())
              .build();
    }
}
