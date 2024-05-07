package store.beatherb.restapi.global.jwt.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;


@Getter
@AllArgsConstructor
public enum JWTErrorCode implements ErrorCode {
    INVALID_TOKEN(401,"JWT_01","토큰이 유효하지 않습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
