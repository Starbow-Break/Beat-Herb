package store.beatherb.restapi.content.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.content.dto.request.DeleteHashTagRequest;
import store.beatherb.restapi.content.dto.request.HashTagSearchNameRequest;
import store.beatherb.restapi.content.dto.request.RegistHashTagRequest;
import store.beatherb.restapi.content.dto.request.UpdateHashTagRequest;
import store.beatherb.restapi.content.dto.response.HashTagListResponse;
import store.beatherb.restapi.content.dto.response.HashTagUseResponse;
import store.beatherb.restapi.content.dto.response.RegistHashTagResponse;
import store.beatherb.restapi.content.dto.response.UpdateHashTagResponse;
import store.beatherb.restapi.content.service.HashTagService;
import store.beatherb.restapi.global.response.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/hashtag")
public class HashTagController {
    private final HashTagService hashTagService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegistHashTagResponse>> registHashTag(@Valid @RequestBody RegistHashTagRequest registHashTagRequest){
        RegistHashTagResponse response = hashTagService.registHashTag(registHashTagRequest);
        ApiResponse<RegistHashTagResponse> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<HashTagListResponse>>> findAllORfindByNameContains(@ModelAttribute HashTagSearchNameRequest hashTagSearchNameRequest){
        log.info("HashTagSearchNameRequest =  [ {} ]",hashTagSearchNameRequest);
        List<HashTagListResponse> list = hashTagService.findAllORfindByNameStartsWith(hashTagSearchNameRequest);
        ApiResponse<List<HashTagListResponse>> response = ApiResponse.successWithData(list);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UpdateHashTagResponse>> updateHashTag(@Valid @RequestBody UpdateHashTagRequest updateHashTagRequest){
        UpdateHashTagResponse response = hashTagService.updateHashTag(updateHashTagRequest);
        ApiResponse<UpdateHashTagResponse> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteHashTag(@Valid @RequestBody DeleteHashTagRequest deleteHashTagRequest){
        hashTagService.deleteHashTag(deleteHashTagRequest);
        ApiResponse<?> apiResponse = ApiResponse.successWithoutData();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HashTagUseResponse>> hashTagUse(@PathVariable Long id){

        HashTagUseResponse hashTagUseResponse = hashTagService.findMemberAndContentByUsingHashTagId(id);
        ApiResponse<HashTagUseResponse> response = ApiResponse.successWithData(hashTagUseResponse);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
