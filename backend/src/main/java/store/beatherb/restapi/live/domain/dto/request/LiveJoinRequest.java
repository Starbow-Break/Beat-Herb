package store.beatherb.restapi.live.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class LiveJoinRequest {
    Long id;
}
