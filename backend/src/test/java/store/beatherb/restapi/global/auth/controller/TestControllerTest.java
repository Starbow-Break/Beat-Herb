package store.beatherb.restapi.global.auth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.jwt.JWTProvider;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RestController
class TestController {
    @GetMapping("/")
    public ResponseEntity<String> test(@LoginUser MemberDTO memberDTO){
        System.out.println("테스트용 컨트롤러입니다.");
        return ResponseEntity.ok(memberDTO.toString());
    }
}


@SpringBootTest
class TestControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JWTProvider jwtProvider;


    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void ArguementResolver테스트_OK() throws Exception {

        Member member = Member.builder()
                .google("email")
                .kakao("kakao")

                .name("wookoo")
                .email("apple")
                .build();
        memberRepository.save(member);
        assertNotNull(member.getId());

        String accessToken = jwtProvider.createAccessToken(member.getId()).getToken();

        mockMvc.perform(get("/")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void ArguementResolver테스트_Fail() throws Exception {

        Member member = Member.builder()
                .google("email")
                .kakao("kakao")

                .name("wookoo")
                .email("apple")
                .build();
        memberRepository.save(member);
        assertNotNull(member.getId());

        mockMvc.perform(get("/")
                        .header("Authorization", "Bearer appple"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }


}