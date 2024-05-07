package store.beatherb.restapi.interest.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class InterestException extends BeatHerbException {
    public InterestException(ErrorCode errorCode){
        super(errorCode);
    }
}
