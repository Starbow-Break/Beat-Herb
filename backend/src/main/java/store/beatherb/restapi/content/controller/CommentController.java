package store.beatherb.restapi.content.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.content.dto.request.RegistCommentRequest;
import store.beatherb.restapi.content.service.CommentService;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.dto.MemberDTO;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registComment(@LoginUser MemberDTO memberDto,@Valid @RequestBody RegistCommentRequest registCommentRequest){
        commentService.registerComment(memberDto, registCommentRequest);
        return ResponseEntity.ok(ApiResponse.successWithoutData());
    }

//    @GetMapping("{contentId}")
//    public ResponseEntity<List<Comment>> getCommentList(@PathVariable Long contentId){
//        return ResponseEntity.ok(commentService.getComments(contentId));
//    }
}
