package store.beatherb.restapi.follow.follower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.follow.domain.Follow;
import store.beatherb.restapi.follow.domain.FollowRepository;
import store.beatherb.restapi.follow.follower.dto.request.DeleteFollowerRequest;
import store.beatherb.restapi.follow.follower.dto.request.RegistFollowerRequest;
import store.beatherb.restapi.follow.follower.dto.response.FollowersResponse;
import store.beatherb.restapi.follow.follower.dto.response.RegistFollowerResponse;
import store.beatherb.restapi.follow.follower.exception.FollowerErrorCode;
import store.beatherb.restapi.follow.follower.exception.FollowerException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public RegistFollowerResponse registFollower(MemberDTO memberDTO, RegistFollowerRequest registFollowerRequest){
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_FIND_ERROR));

        Member followMember = memberRepository.findById(registFollowerRequest.getId()).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_FIND_ERROR));

        Optional<Follow> follow = followRepository.findByMemberIdAndFollowMemberId(member.getId(), followMember.getId());

        //이미 팔로우 한 사람임
        if(follow.isPresent()){
            throw new FollowerException(FollowerErrorCode.ALREADY_FOLLOWER);
        }

        //팔로우한 사람이 아니라면
        Follow notFollow = Follow.builder()
                .member(member)
                .followMember(followMember)
                .build();

        followRepository.save(notFollow);

        return RegistFollowerResponse.toDto(notFollow);
    }

    //나의 팔로워들을 리스트로 가져온다
    public List<FollowersResponse> getFollowerList(MemberDTO memberDTO){
        List<Follow> listFollow = followRepository.findByMemberId(memberDTO.getId()).orElseThrow();

        List<FollowersResponse> listFollowers = new ArrayList<>();
        for(Follow follow : listFollow){
            Member member = follow.getFollowMember();

            listFollowers.add(FollowersResponse.builder().id(member.getId()).name(member.getName()).build());
        }
        return listFollowers;
    }

    public void deleteFollower(MemberDTO memberDTO, DeleteFollowerRequest deleteFollowerRequest){
        followRepository.delete(
                followRepository.findByMemberIdAndFollowMemberId(memberDTO.getId(), deleteFollowerRequest.getId())
                        .orElseThrow(() -> new FollowerException(FollowerErrorCode.FOLLOWER_FIND_ERROR))
        );
    }
}
