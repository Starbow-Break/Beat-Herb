package store.beatherb.restapi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BeatHerbErrorCode implements ErrorCode{

    INTERNAL_SERVER_ERROR(500, "UTIL_01", "서버에서 문제가 발생했습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
