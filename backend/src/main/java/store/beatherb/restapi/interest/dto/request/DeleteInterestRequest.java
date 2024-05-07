package store.beatherb.restapi.interest.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Builder
@AllArgsConstructor
public class DeleteInterestRequest {
    @NotNull(message = "hashTagId 는 null 이여선 안됩니다.")
    private Long hashTagId;
}
