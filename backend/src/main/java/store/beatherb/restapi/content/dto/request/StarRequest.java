package store.beatherb.restapi.content.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class StarRequest {
    @NotNull(message = "contentId 가 필요합니다.")
    Long contentId;
}
