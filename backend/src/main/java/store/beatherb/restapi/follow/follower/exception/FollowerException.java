package store.beatherb.restapi.follow.follower.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class FollowerException extends BeatHerbException {
    public FollowerException(ErrorCode errorCode){
        super(errorCode);
    }
}
