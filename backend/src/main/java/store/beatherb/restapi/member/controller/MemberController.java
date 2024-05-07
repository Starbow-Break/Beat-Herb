package store.beatherb.restapi.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.dto.request.EditRequest;
import store.beatherb.restapi.member.dto.request.SignInRequest;
import store.beatherb.restapi.member.dto.request.SignUpRequest;
import store.beatherb.restapi.member.dto.response.MemberDetailResponse;
import store.beatherb.restapi.member.dto.response.MemberSearchResponse;
import store.beatherb.restapi.member.service.MemberInfoService;
import store.beatherb.restapi.member.service.MemberService;
import java.util.List;

@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MemberSearchResponse>>> searchMemberByNickName(@RequestParam String nickname){
        log.info("call here = [{}]",nickname);
        List<MemberSearchResponse> list =  memberService.findByNickName(nickname);
        ApiResponse<List<MemberSearchResponse>> response = ApiResponse.successWithData(list);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailResponse>> detailMemberById(@LoginUser(required = false) MemberDTO memberDTO,@PathVariable  Long id){
        MemberDetailResponse memberDetailResponse = memberService.detailMemberById(memberDTO,id);
        ApiResponse<MemberDetailResponse> response = ApiResponse.successWithData(memberDetailResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ApiResponse<?> edit(@LoginUser MemberDTO memberDTO, @ModelAttribute EditRequest editRequest){
        memberInfoService.edit(memberDTO, editRequest);
        return ApiResponse.successWithoutData();
    }

    @PostMapping("/signup")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        memberService.signUp(signUpRequest);
        return ApiResponse.successWithoutData();
    }

    @PostMapping("/signin")
    public ApiResponse<?> signIn(@RequestBody SignInRequest signInRequest){
        memberService.signIn(signInRequest);
        return ApiResponse.successWithoutData();
    }

    @GetMapping("/verify")
    public ApiResponse<VerifyTokenResponse> verify(@RequestParam String token){
        return ApiResponse.successWithData( memberService.verify(token));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){

        // 다운로드할 때 사용할 HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        Resource resource = memberService.getImage(id);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        // 다운로드할 파일의 MIME 타입 설정
        MediaType mediaType = MediaType.IMAGE_PNG;

        // 응답 생성
        return ResponseEntity.ok()
                .contentType(mediaType)
                .headers(headers)
                .body(resource);

    }

}
