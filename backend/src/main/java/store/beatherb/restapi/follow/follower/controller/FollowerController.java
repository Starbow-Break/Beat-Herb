package store.beatherb.restapi.follow.follower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.follow.follower.dto.request.DeleteFollowerRequest;
import store.beatherb.restapi.follow.follower.dto.request.RegistFollowerRequest;
import store.beatherb.restapi.follow.follower.dto.response.FollowersResponse;
import store.beatherb.restapi.follow.follower.dto.response.RegistFollowerResponse;
import store.beatherb.restapi.follow.follower.service.FollowerService;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follower")
public class FollowerController {
    private final FollowerService followService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegistFollowerResponse>> registFollower(@LoginUser MemberDTO memberDTO, @Valid @RequestBody RegistFollowerRequest registFollowRequest){
        RegistFollowerResponse response = followService.registFollower(memberDTO, registFollowRequest);
        ApiResponse<RegistFollowerResponse> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FollowersResponse>>> getFollowerList(@LoginUser MemberDTO memberDTO){
        List<FollowersResponse> response = followService.getFollowerList(memberDTO);
        ApiResponse<List<FollowersResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFollower(@LoginUser MemberDTO memberDTO, @Valid @RequestBody DeleteFollowerRequest deleteFollowerRequest){
        followService.deleteFollower(memberDTO, deleteFollowerRequest);
        ApiResponse<?> apiResponse = ApiResponse.successWithoutData();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
