package store.beatherb.restapi.global.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.jwt.JWTProvider;
import store.beatherb.restapi.global.jwt.dto.exception.JWTErrorCode;
import store.beatherb.restapi.global.jwt.dto.exception.JWTException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {



        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if (httpServletRequest != null) {
            String token = httpServletRequest.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                if (jwtProvider.checkToken(token)) {
                    long memberId = jwtProvider.getMemberPrimaryKeyId(token);
                    Member member = memberRepository.findById(memberId).orElseThrow(
                            ()->{
                                throw new JWTException(JWTErrorCode.INVALID_TOKEN);
                            }
                    );
                    return MemberDTO.toDTO(member);

                }
            }
        }

        LoginUser annotation = parameter.getParameterAnnotation(LoginUser.class);
        if (annotation != null && !annotation.required()) {
            return null;
        }

        throw new JWTException(JWTErrorCode.INVALID_TOKEN);
    }
}