package store.beatherb.restapi.follow.follower.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum FollowerErrorCode implements ErrorCode {
    FOLLOWER_FIND_ERROR(400, "FOLLOWER_01", "팔로워 정보를 찾을 수 없습니다."),
    ALREADY_FOLLOWER(400, "FOLLOWER_02", "이미 등록되어 있는 팔로워입니다.");

    private int statusCode;
    private String errorCode;
    private String message;
}
