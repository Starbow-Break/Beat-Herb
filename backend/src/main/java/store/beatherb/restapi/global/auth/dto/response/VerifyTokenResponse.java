package store.beatherb.restapi.global.auth.dto.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class VerifyTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
    private String nickname;
    private Long id;

    private String socket;

    @Builder
    public VerifyTokenResponse(String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn, String nickname, Long id, String socket) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.nickname = nickname;
        this.id = id;
        this.socket = "wss://socket.beatherb.store/socket?token="+socket;
    }
}
