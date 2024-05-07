package store.beatherb.restapi.content.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.ContentRepository;
import store.beatherb.restapi.content.domain.StarRepository;
import store.beatherb.restapi.content.domain.Star;
import store.beatherb.restapi.content.dto.request.StarRequest;
import store.beatherb.restapi.content.dto.response.ContentStarCheckResponse;
import store.beatherb.restapi.content.exception.ContentErrorCode;
import store.beatherb.restapi.content.exception.ContentException;
import store.beatherb.restapi.content.exception.FavoriteErrorCode;
import store.beatherb.restapi.content.exception.FavoriteException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarService {
    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;

    public void starContentService(MemberDTO memberDTO, StarRequest starRequest) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        Content content = contentRepository.findById(starRequest.getContentId()).orElseThrow(
                () -> {
                    return new ContentException(ContentErrorCode.CONTENT_NOT_FOUND);
                }
        );
        starRepository.findByContentAndMember(content, member).ifPresent(
                star -> {
                    throw new FavoriteException(FavoriteErrorCode.ALREADY_EXISTS);
                }
        );
        Star star = Star.builder()
                .content(content)
                .member(member)
                .build();

        starRepository.save(star);
    }

//    public void removeFavoriteContentService(FavoriteDTO favoriteDTO) {
//        Star favorite = favoriteRepository.findByContentIdAndMemberId(favoriteDTO.getContent().getId(), favoriteDTO.getMember().getId()).orElseThrow(
//                () -> {
//                    return new FavoriteException(FavoriteErrorCode.FAVORITE_FIND_ERROR);
//                });
//        favoriteRepository.deleteById(favorite.getId());
//    }

    public void removeStarContentService(MemberDTO memberDTO, StarRequest starRequest) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        Content content = contentRepository.findById(starRequest.getContentId()).orElseThrow(
                () -> {
                    return new ContentException(ContentErrorCode.CONTENT_NOT_FOUND);
                }
        );
        Star favorite = starRepository.findByContentAndMember(content, member).orElseThrow(
                () -> {
                    return new FavoriteException(FavoriteErrorCode.FAVORITE_FIND_ERROR);
                }
        );
        starRepository.delete(favorite);
    }

    @Transactional
    public List<ContentStarCheckResponse> checkUserStar(MemberDTO memberDTO, List<Long> contentIdList) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        List<Star> starList = starRepository.findByMemberAndContentIdIn(member, contentIdList);

        List<ContentStarCheckResponse> contentStarCheckResponses = new ArrayList<>();

        for (Star star : starList) {
            Content content = star.getContent();
            contentStarCheckResponses.add(
                    ContentStarCheckResponse.builder()
                            .id(content.getId())
                            .star(true)
                            .build()
            );
        }
        return contentStarCheckResponses;
    }
}
