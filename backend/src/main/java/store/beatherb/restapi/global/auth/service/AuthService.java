package store.beatherb.restapi.global.auth.service;

import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;

public interface AuthService {
    VerifyTokenResponse verify(String token);
    VerifyTokenResponse generateVerifyTokenResponse(long id);
}
