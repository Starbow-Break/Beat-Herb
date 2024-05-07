package store.beatherb.restapi.follow.following.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum FollowingErrorCode implements ErrorCode {
    FOLLOWING_NOT_FOUND(400, "FOLLOWING_01", "팔로잉 정보를 찾을 수 없습니다.");

    private int statusCode;
    private String errorCode;
    private String message;
}
