package store.beatherb.restapi.content.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteHashTagRequest {
    @NotNull(message = "hashTagId는 null 값이 들어오면 안됩니다.")
    private Long id;
}
