package store.beatherb.restapi.follow.following.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.follow.domain.Follow;
import store.beatherb.restapi.follow.domain.FollowRepository;
import store.beatherb.restapi.follow.follower.dto.response.FollowersResponse;
import store.beatherb.restapi.follow.follower.exception.FollowerErrorCode;
import store.beatherb.restapi.follow.follower.exception.FollowerException;
import store.beatherb.restapi.follow.following.dto.response.FollowingResponse;
import store.beatherb.restapi.follow.following.exception.FollowingErrorCode;
import store.beatherb.restapi.follow.following.exception.FollowingException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowingService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public List<FollowingResponse> getFollowingList(MemberDTO memberDTO) {
        List<Follow> list = followRepository.findByFollowMemberId(memberDTO.getId()).orElseThrow();

        List<FollowingResponse> followingList = new ArrayList<>();
        for (Follow follow : list) {
            Member member = follow.getMember();

            followingList.add(FollowingResponse.builder().id(member.getId()).name(member.getName()).build());
        }

        return followingList;
    }
}
