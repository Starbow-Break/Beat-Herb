package store.beatherb.restapi.interest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.global.response.ApiResponse;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.interest.dto.request.DeleteInterestRequest;
import store.beatherb.restapi.interest.dto.request.RegistInterestRequest;
import store.beatherb.restapi.interest.dto.response.RegistInterestResponse;
import store.beatherb.restapi.interest.service.InterestService;
import store.beatherb.restapi.member.dto.MemberDTO;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestController {
    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registInterest(@LoginUser MemberDTO memberDto, @Valid @RequestBody RegistInterestRequest registInterestRequest){
        interestService.registInterest(memberDto, registInterestRequest);
        ApiResponse<?> apiResponse = ApiResponse.successWithoutData();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteInterest(@LoginUser MemberDTO memberDto, @Valid @RequestBody DeleteInterestRequest deleteInterestRequest){
        interestService.deleteInterest(memberDto, deleteInterestRequest);
        ApiResponse<?> apiResponse = ApiResponse.successWithoutData();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
