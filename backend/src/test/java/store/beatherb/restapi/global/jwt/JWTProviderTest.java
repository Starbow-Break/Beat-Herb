package store.beatherb.restapi.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.jwt.dto.TokenDTO;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JWT 토큰을 검증한다.")
@SpringBootTest
class JWTProviderTest {
    @Autowired
    JWTProvider jwtProvider;

    @Value("${jwt.salt}")
    private String salt;

    @Test
    void AccessToken_생성_검증() throws Exception{
        TokenDTO accessToken = jwtProvider.createAccessToken(1);
        assertTrue(accessToken.getExpired() > 0);
        boolean checked = jwtProvider.checkToken(accessToken.getToken());
        long jwtId = jwtProvider.getMemberPrimaryKeyId(accessToken.getToken());
        assertTrue(checked);
        assertEquals(jwtId,1L);
        byte[] key = salt.getBytes("UTF-8");
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken.getToken());
        Map<String, Object> value = claims.getBody();
        System.out.println(accessToken.getToken());
        int exp = (Integer)value.get("exp");
        assertEquals(exp,accessToken.getExpired());
    }

    @Test
    void RefreshToken_생성_검증() throws Exception{
        TokenDTO refreshToken = jwtProvider.createRefreshToken(1);
        assertTrue(refreshToken.getExpired() > 0);
        boolean checked = jwtProvider.checkToken(refreshToken.getToken());
        long jwtId = jwtProvider.getMemberPrimaryKeyId(refreshToken.getToken());
        assertTrue(checked);
        assertEquals(jwtId,1L);
        byte[] key = salt.getBytes("UTF-8");
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken.getToken());
        Map<String, Object> value = claims.getBody();
        System.out.println(refreshToken.getToken());
        int exp = (Integer)value.get("exp");
        assertEquals(exp,refreshToken.getExpired());
    }


}