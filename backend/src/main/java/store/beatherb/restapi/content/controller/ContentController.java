package store.beatherb.restapi.content.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;
import store.beatherb.restapi.content.dto.request.CreatorAgreeRequest;
import store.beatherb.restapi.content.dto.request.ContentUploadRequest;
import store.beatherb.restapi.content.dto.response.ContentPopularityResponse;
import store.beatherb.restapi.content.dto.response.ContentTitleSearchResponse;
import store.beatherb.restapi.content.dto.response.ContentUploadRespone;
import store.beatherb.restapi.content.dto.response.ContentResponse;
import store.beatherb.restapi.content.service.ContentService;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.util.List;

@RestController
@RequestMapping("/content")
@Slf4j
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ContentTitleSearchResponse>> searchByTitle(@RequestParam(required = false) String title, @RequestParam(name="id", required = false) List<Long> hashTagIds){
        ContentTitleSearchResponse contentTitleSearchResponse = contentService.searchByTitle(title, hashTagIds);
        return ResponseEntity.ok(ApiResponse.successWithData(contentTitleSearchResponse));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){


        // 다운로드할 때 사용할 HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        Resource resource = contentService.getImage(id);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        // 다운로드할 파일의 MIME 타입 설정
        MediaType mediaType = MediaType.IMAGE_PNG;

        // 응답 생성
        return ResponseEntity.ok()
                .contentType(mediaType)
                .headers(headers)
                .body(resource);

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Content>>> contentsOrderByHit(){
        List<Content> response= contentService.getContentsOrderByHit();
        return ResponseEntity.ok(ApiResponse.successWithData(response));
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> showDetail(@LoginUser(required = false) MemberDTO memberDTO, @PathVariable Long contentId){

        return ResponseEntity.ok(ApiResponse.successWithData(contentService.showDetailByContentId(memberDTO,contentId)));
    }

    @GetMapping("/play/{contentNumber}")
    public ResponseEntity<Resource> getPlayListByContentNumber(@PathVariable Integer contentNumber) {
        Resource resource = contentService.getPlayList(contentNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;");
        headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"));
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/play/{contentNumber}/{segmentName:.+}")
    public ResponseEntity<Resource> getSegment(@PathVariable Integer contentNumber,@PathVariable String segmentName) {

        Resource resource = contentService.getSegmentByContentNumberAndSegmentName(contentNumber,segmentName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> upload(@Parameter(hidden = true) @LoginUser MemberDTO memberDTO, @Valid  @ModelAttribute  ContentUploadRequest contentUploadRequest){
        //TODO : 수정 필요!!!!!!!
        ContentUploadRespone contentUploadRespone = contentService.uploadContent(memberDTO,contentUploadRequest);
        ApiResponse<ContentUploadRespone> response = ApiResponse.of(HttpStatus.CREATED,contentUploadRespone);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/agree")
    public ResponseEntity<ApiResponse<?>> contentAgreeAboutCreator(@LoginUser MemberDTO memberDTO, @Valid @RequestBody CreatorAgreeRequest creatorAgreeRequest){


        contentService.contentAgreeAboutCreator(memberDTO, creatorAgreeRequest);

        ApiResponse<?> response = ApiResponse.successWithoutData();
        return ResponseEntity.status(response.getCode()).body(response);
    }
//    @GetMapping("/test")
//    public ResponseEntity<?> test(){
//        contentService.makeSequence();
//
//        return ResponseEntity.ok("good");
//    }

    @GetMapping("/popularity")
    public ResponseEntity<ApiResponse<ContentPopularityResponse>> getPopularity(){
        ContentPopularityResponse response = contentService.getPopularity();
        ApiResponse<ContentPopularityResponse> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/daily/vocal")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityDailyVocal(){
        List<ContentResponse> response = contentService.getPopularityDaily(ContentTypeEnum.VOCAL);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/weekly/vocal")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityWeeklyVocal(){
        List<ContentResponse> response = contentService.getPopularityWeekly(ContentTypeEnum.VOCAL);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/monthly/vocal")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityMonthlyVocal(){
        List<ContentResponse> response = contentService.getPopularityMonthly(ContentTypeEnum.VOCAL);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/daily/melody")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityDailyMelody(){
        List<ContentResponse> response = contentService.getPopularityDaily(ContentTypeEnum.MELODY);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/weekly/melody")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityWeeklyMelody(){
        List<ContentResponse> response = contentService.getPopularityWeekly(ContentTypeEnum.MELODY);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/monthly/melody")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityMonthlyMelody(){
        List<ContentResponse> response = contentService.getPopularityMonthly(ContentTypeEnum.MELODY);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/daily/soundtrack")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityDailySoundTrack(){
        List<ContentResponse> response = contentService.getPopularityDaily(ContentTypeEnum.SOUNDTRACK);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/weekly/soundtrack")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityWeeklySoundTrack(){
        List<ContentResponse> response = contentService.getPopularityWeekly(ContentTypeEnum.SOUNDTRACK);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/monthly/soundtrack")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getPopularityMonthlySoundTrack(){
        List<ContentResponse> response = contentService.getPopularityMonthly(ContentTypeEnum.SOUNDTRACK);
        ApiResponse<List<ContentResponse>> apiResponse = ApiResponse.successWithData(response);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
