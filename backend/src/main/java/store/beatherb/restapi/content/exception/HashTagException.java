package store.beatherb.restapi.content.exception;

import store.beatherb.restapi.content.domain.HashTagRepository;
import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class HashTagException extends BeatHerbException {
    public HashTagException(ErrorCode errorCode){
        super(errorCode);
    }
}
