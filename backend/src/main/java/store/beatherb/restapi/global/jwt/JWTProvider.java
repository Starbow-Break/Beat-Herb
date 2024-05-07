package store.beatherb.restapi.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.exception.UnAuthorizedException;
import store.beatherb.restapi.global.jwt.dto.TokenDTO;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JWTProvider {
    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;

    public TokenDTO createAccessToken(long id){


        String token = create(id, "access-token", accessTokenExpireTime);
        return TokenDTO.builder()
                .token(token)
                .expired(this.accessTokenExpiresIn)
                .build();
    }

    public TokenDTO createRefreshToken(long id){
        String token = create(id, "refresh-token", refreshTokenExpireTime);
        return TokenDTO.builder()
                .token(token)
                .expired(this.refreshTokenExpiresIn)
                .build();
    }

    //Token 발급
    //payload db에서 increment해주는 pk 값을 넣어줄것임
    //"id" : 1
    private String create(long id, String subject, long expireTime){
        //payload 설정 : 생성일(IssuedAt), 유효기간(Expiration)
        //토큰 제목 (subject), 데이터 (claim) 정보 셋팅
        Date expTime = new Date(System.currentTimeMillis() + expireTime);
        if(subject.equals("access-token")){
            this.accessTokenExpiresIn = expTime.getTime()/1000;
        }
        else{
            this.refreshTokenExpiresIn = expTime.getTime()/1000;
        }
        Claims claims = Jwts.claims()
                .setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .setIssuedAt(new Date()) // 생성일 설정
                .setExpiration(expTime); // 만료일 설정 (유효기간)

        //저장할 data의 key, value
        claims.put("id", id);

        String jwt = Jwts.builder()
                .setHeaderParam("typ","JWT").setClaims(claims) // Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅
                .signWith(SignatureAlgorithm.HS256, this.generateKey()) // Signature 설정 : secret key를 활용한 암호화
                .compact(); // 직렬화 처리

        return jwt;
    }

    // Signature에 설정에 들어갈 key 생성
    private byte[] generateKey(){
        byte[] key = null;
        try{
            key = salt.getBytes("UTF-8");
        } catch(UnsupportedEncodingException e){
            if(log.isInfoEnabled()){
                e.printStackTrace();
            } else{
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

    //전달 받은 토큰이 유효한 토큰인지 확인하고 문제가 있다면 UnauthorizedException 발생
    public boolean checkToken(String token){
        try{
            //setSigningKey : JWS 서명 검증을 위한 secret key 세팅
            //parseClaimsJws : 파싱하여 원본 jws 만들기
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
            //Claims는 Map의 구현체 형태
            log.debug("claims: {}", claims);

            return true;
        } catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public Long getMemberPrimaryKeyId(String token){
        Jws<Claims> claims = null;
        try{
            claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new UnAuthorizedException();
        }

        Map<String, Object> value = claims.getBody();
        log.info("value : {}", value);


        return ((Number)value.get("id")).longValue();
    }

//    public VerifyTokenResponse generateVerifyTokenResponse(long id){
//        String accessToken = createAccessToken(id);
//        String refreshToken = createRefreshToken(id);
//
//        return VerifyTokenResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .accessTokenExpiresIn(this.accessTokenExpiresIn)
//                .refreshTokenExpiresIn(this.refreshTokenExpiresIn)
//                .build();
//    }
}
