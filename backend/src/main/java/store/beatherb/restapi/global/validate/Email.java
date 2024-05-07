package store.beatherb.restapi.global.validate;


import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.regex.Pattern;

public class Email {
    private static final String EMAIL_FORMAT="^(.+)@(.+).(.+)$";
    private static final Pattern EMAIL_PATTERN=Pattern.compile(EMAIL_FORMAT);

    public static void validate(String email){
        if(!EMAIL_PATTERN.matcher(email).matches()){
          throw new MemberException(MemberErrorCode.EMAIL_IS_NOT_VALID);
        }
    }
}
