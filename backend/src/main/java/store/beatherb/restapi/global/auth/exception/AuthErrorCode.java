package store.beatherb.restapi.global.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    PERMISSION_DENIED(403, "AUTH_01", "권한이 부족합니다."),
    INVALID_TOKEN(404,"AUTH_02","유효하지 않은 토큰입니다");



    private final int statusCode;
    private final String errorCode;
    private final String message;
}
