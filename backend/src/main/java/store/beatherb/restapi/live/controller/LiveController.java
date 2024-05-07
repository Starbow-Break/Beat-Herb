package store.beatherb.restapi.live.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.live.domain.Live;
import store.beatherb.restapi.live.domain.dto.request.LiveCreateRequest;
import store.beatherb.restapi.live.domain.dto.request.LiveJoinRequest;
import store.beatherb.restapi.live.domain.dto.response.LiveJoinResponse;
import store.beatherb.restapi.live.dto.response.LiveDetailResponse;
import store.beatherb.restapi.live.service.LiveService;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.openvidu.property.OpenviduProperties;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/live")
public class LiveController {
    private final LiveService liveService;

    @GetMapping("{id}")
    public ResponseEntity<Live> liveDetail(@PathVariable Long id){
        Live live = liveService.liveDetail(id);

        return ResponseEntity.ok(live);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createLive(@LoginUser MemberDTO memberDTO,@Valid @RequestBody LiveCreateRequest liveCreateRequest){

        LiveJoinResponse liveJoinResponse = liveService.createLive(memberDTO,liveCreateRequest);
        ApiResponse<LiveJoinResponse> response = ApiResponse.of(HttpStatus.CREATED,liveJoinResponse);
        return ResponseEntity.status(response.getCode()).body(response);

    }
    @GetMapping("join/{id}")
    public ResponseEntity<ApiResponse<LiveJoinResponse>> joinLive(@LoginUser MemberDTO memberDTO, @PathVariable Long id){
        LiveJoinRequest liveJoinRequest = LiveJoinRequest.builder().id(id).build();

        LiveJoinResponse response = liveService.joinLive(memberDTO,liveJoinRequest);
        return ResponseEntity.ok(ApiResponse.successWithData(response));
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteLive(@LoginUser MemberDTO memberDTO){
        liveService.deleteLive(memberDTO);

        return ResponseEntity.ok(ApiResponse.successWithoutData());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LiveDetailResponse>>> liveList(){
        List<LiveDetailResponse> response = liveService.liveList();
        ApiResponse<List<LiveDetailResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
