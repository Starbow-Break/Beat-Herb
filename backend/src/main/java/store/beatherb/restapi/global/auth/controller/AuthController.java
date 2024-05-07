package store.beatherb.restapi.global.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.auth.service.AuthServiceImpl;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthServiceImpl authService;
    @GetMapping("/auth/verify")
    public ResponseEntity<VerifyTokenResponse> verify(@RequestParam String token){
        VerifyTokenResponse verifyTokenResponse = authService.verify(token);

        return ResponseEntity.ok(verifyTokenResponse);
    }
}
