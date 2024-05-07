package store.beatherb.restapi.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateHashTagRequest {
    @NotNull(message = "hashTagId는 null 값이 들어오면 안됩니다.")
    private Long id;
    @NotBlank
    private String name;
}
