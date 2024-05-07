package store.beatherb.restapi.follow.following.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class FollowingException extends BeatHerbException {
    public FollowingException(ErrorCode errorCode){
        super(errorCode);
    }
}
