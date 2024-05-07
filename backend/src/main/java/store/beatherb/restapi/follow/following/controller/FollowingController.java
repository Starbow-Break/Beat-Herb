package store.beatherb.restapi.follow.following.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.beatherb.restapi.follow.following.dto.response.FollowingResponse;
import store.beatherb.restapi.follow.following.service.FollowingService;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/following")
public class FollowingController {
    private final FollowingService followingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FollowingResponse>>> getFollowingList(@LoginUser MemberDTO memberDTO){
        List<FollowingResponse> response = followingService.getFollowingList(memberDTO);
        ApiResponse<List<FollowingResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
