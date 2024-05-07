package store.beatherb.restapi.interest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.content.domain.HashTagRepository;
import store.beatherb.restapi.content.exception.HashTagErrorCode;
import store.beatherb.restapi.content.exception.HashTagException;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.interest.domain.InterestRepository;
import store.beatherb.restapi.interest.dto.request.DeleteInterestRequest;
import store.beatherb.restapi.interest.dto.request.RegistInterestRequest;
import store.beatherb.restapi.interest.dto.response.RegistInterestResponse;
import store.beatherb.restapi.interest.exception.InterestErrorCode;
import store.beatherb.restapi.interest.exception.InterestException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;
    private final HashTagRepository hashTagRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void registInterest(@LoginUser MemberDTO memberDto, RegistInterestRequest registInterestRequest){
        HashTag hashTag = hashTagRepository.findById(registInterestRequest.getHashTagId())
                .orElseThrow(() -> new HashTagException(HashTagErrorCode.HASHTAG_IS_NOT_EXIST));

        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_FIND_ERROR));
        //이미 좋아요했으면 throw
        interestRepository.findByMemberAndHashTag(member,hashTag).ifPresent(interest -> {
            throw new InterestException(InterestErrorCode.ALREADY_INTEREST);
        });

        Interest interest = Interest.builder()
                .hashTag(hashTag)
                .member(member)
                .build();

        interestRepository.save(interest);
    }

    @Transactional
    public void deleteInterest(MemberDTO memberDTO, DeleteInterestRequest deleteInterestRequest){
        interestRepository.delete(interestRepository.findByMemberIdAndHashTagId(memberDTO.getId(), deleteInterestRequest.getHashTagId())
                .orElseThrow(() -> new InterestException(InterestErrorCode.INTEREST_IS_NOT_EXIST)));
    }
}
