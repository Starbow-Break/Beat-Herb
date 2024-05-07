package store.beatherb.restapi.live.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@AllArgsConstructor
@Getter
public enum LiveErrorCode implements ErrorCode {
    LIVE_IS_NOT_EXIST(400, "LIVE_01", "해당 라이브가 존재하지 않습니다."),
    LIVE_ALREADY_EXIST(409,"LIVE_02","이미 라이브 진행 중 입니다.");

    private int statusCode;
    private String errorCode;
    private String message;
}
