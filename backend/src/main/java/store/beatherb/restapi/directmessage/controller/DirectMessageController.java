package store.beatherb.restapi.directmessage.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageFetchRequest;
import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageSendRequest;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageFetchResponse;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageSendResponse;
import store.beatherb.restapi.directmessage.service.DirectMessageService;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService directMessageService;


    @PostMapping
    public ResponseEntity<ApiResponse<DirectMessageSendResponse>> sendDirectMessage(@LoginUser MemberDTO memberDTO,
                                                                                    @Valid @RequestBody DirectMessageSendRequest directMessageSendRequest){


        DirectMessageSendResponse directMessageSendResponse = directMessageService.sendDirectMessage(memberDTO,directMessageSendRequest);
        ApiResponse<DirectMessageSendResponse> apiResponse = ApiResponse.of(200, directMessageSendResponse);
        return ResponseEntity.ok(apiResponse);
    }


    //time 뜯어오기.
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getDirectMessageByTime(@LoginUser MemberDTO memberDTO, @Valid DirectMessageFetchRequest directMessageFetchRequest){


        List<DirectMessageFetchResponse> list = directMessageService.findBySenderOrReceiverAndCreatedAtAfter(memberDTO, directMessageFetchRequest);
        return ResponseEntity.ok(ApiResponse.of(200,list));

    }

}
