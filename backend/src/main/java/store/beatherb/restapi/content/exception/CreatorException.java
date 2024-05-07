package store.beatherb.restapi.content.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class CreatorException extends BeatHerbException {
    public CreatorException(ErrorCode errorCode){
        super(errorCode);
    }
}
