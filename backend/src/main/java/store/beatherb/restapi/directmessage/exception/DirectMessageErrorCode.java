package store.beatherb.restapi.directmessage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;


@Getter
@AllArgsConstructor
public enum DirectMessageErrorCode implements ErrorCode {
    SENDER_EQUAL_RECEIVER(409,"DM_01","발송자와 수신자가 일치합니다."),
    RECEIVER_NOT_ALLOW_DIRECT_MESSAGE(409,"DM_02","수신자가 DM 을 활성하지 않았습니다."),
    DIRECT_MESSAGE_SEND_FAIL(400,"DM_03","알 수 없는 오류로 발송에 실패했습니다");
    private final int statusCode;
    private final String errorCode;
    private final String message;
}
