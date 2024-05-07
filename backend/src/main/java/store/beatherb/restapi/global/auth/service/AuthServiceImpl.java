package store.beatherb.restapi.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.global.auth.domain.*;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.auth.exception.AuthErrorCode;
import store.beatherb.restapi.global.auth.exception.AuthException;
import store.beatherb.restapi.global.jwt.JWTProvider;
import store.beatherb.restapi.global.jwt.dto.TokenDTO;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.domain.Verify;
import store.beatherb.restapi.member.domain.VerifyRepository;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;
import store.beatherb.restapi.socket.service.SocketTokenValidService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JWTProvider jwtProvider;
    private final VerifyRepository verifyRepository;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SocketTokenValidService socketTokenValidService;

    //uuid가 유효한지 확인
    public VerifyTokenResponse verify(String token) {
        // uuid 정보가 table에 존재하는지 확인
        Verify verify = verifyRepository.findByUuid(token).orElseThrow(
                () -> {
                    return new AuthException(AuthErrorCode.INVALID_TOKEN);
                }
        );

        Member member = verify.getMember().orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.TOKEN_IS_NOT_VALID);
                }
        );

        return generateVerifyTokenResponse(member.getId());

//        Member member = memberRepository.findById(auth.getMember().getId()).orElseThrow();
//        String name = member.getName();
//
//        //generateVerifyTokenResponse 메서드는
//        //AccessToken, RefreshToken을 생성한 후
//        //AccessToken, RefreshToken, AccessTokenExpireTime에 대한 정보를 담아서 넘겨준다.
//        TokenDTO accessToken = jwtProvider.createAccessToken(id);
//        TokenDTO refreshToken = jwtProvider.createRefreshToken(id);
//
//        //redis 에 저장
//        refreshTokenRepository.save(RefreshToken.builder()
//                .id(auth.getMember().getId())
//                .value(refreshToken.getToken())
//                .build());
//
//        return VerifyTokenResponse.builder()
//                .accessToken(accessToken.getToken())
//                .accessTokenExpiresIn(accessToken.getExpired())
//                .refreshToken(refreshToken.getToken())
//                .refreshTokenExpiresIn(refreshToken.getExpired())
//                .name(name)
//                .build();
    }
    public VerifyTokenResponse generateVerifyTokenResponse(long id){
        Member member = memberRepository.findById(id).orElseThrow();
        String nickname = member.getNickname();

        //generateVerifyTokenResponse 메서드는
        //AccessToken, RefreshToken을 생성한 후
        //AccessToken, RefreshToken, AccessTokenExpireTime에 대한 정보를 담아서 넘겨준다.
        TokenDTO accessToken = jwtProvider.createAccessToken(id);
        TokenDTO refreshToken = jwtProvider.createRefreshToken(id);
        String uuid = socketTokenValidService.registMemberSocket(id);

        //redis 에 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .id(id)
                .value(refreshToken.getToken())
                .build());
        return VerifyTokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiresIn(accessToken.getExpired())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiresIn(refreshToken.getExpired())
                .nickname(nickname)
                .id(id)
                .socket(uuid)
                .build();
    }
}
