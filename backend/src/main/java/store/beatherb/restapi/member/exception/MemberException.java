package store.beatherb.restapi.member.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class MemberException extends BeatHerbException {
    public MemberException(ErrorCode errorCode){
        super(errorCode);
    }
}
