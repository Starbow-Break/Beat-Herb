package store.beatherb.restapi.directmessage.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class DirectMessageException extends BeatHerbException {
    public DirectMessageException(ErrorCode errorCode){
        super(errorCode);
    }
}
