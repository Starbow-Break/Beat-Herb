package store.beatherb.restapi.live.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.ContentRepository;
import store.beatherb.restapi.content.exception.ContentErrorCode;
import store.beatherb.restapi.content.exception.ContentException;
import store.beatherb.restapi.live.domain.*;
import store.beatherb.restapi.live.domain.dto.request.LiveCreateRequest;
import store.beatherb.restapi.live.domain.dto.request.LiveJoinRequest;
import store.beatherb.restapi.live.domain.dto.response.LiveJoinResponse;
import store.beatherb.restapi.live.dto.response.LiveContentResponse;
import store.beatherb.restapi.live.dto.response.LiveDetailResponse;
import store.beatherb.restapi.live.dto.response.MasterMemberResponse;
import store.beatherb.restapi.live.exception.LiveErrorCode;
import store.beatherb.restapi.live.exception.LiveException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;
import store.beatherb.restapi.openvidu.dto.response.OpenViduJoinSessionResponse;
import store.beatherb.restapi.openvidu.service.OpenviduService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService {
    private final LiveRepository liveRepository;
    private final MemberRepository memberRepository;

    private final OpenviduService openviduService;
    private final ContentRepository contentRepository;
    private final LiveContentRepository liveContentRepository;

    private final GuestRepository guestRepository;


    @Transactional
    public Live liveDetail(Long id) {
        Live live = liveRepository.findById(id).orElseThrow(() -> new LiveException(LiveErrorCode.LIVE_IS_NOT_EXIST));

        return live;
    }

    @Transactional
    public LiveJoinResponse createLive(MemberDTO memberDTO, LiveCreateRequest liveCreateRequest) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        liveRepository.findByMember(member).ifPresent(m -> {
            throw new LiveException(LiveErrorCode.LIVE_ALREADY_EXIST);
        });

        List<Guest> guestList = new ArrayList<>();
        for (Long guestId : liveCreateRequest.getGuestIdList()) {
            Member guestMember = memberRepository.findById(guestId).orElseThrow(
                    () -> {
                        return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                    }
            );
            Guest guest = Guest.builder()
                    .member(guestMember)
                    .agree(false)
                    .build();

            guestList.add(guest);
        }
        List<LiveContent> liveContentList = new ArrayList<>();
        for (Long contentId : liveCreateRequest.getContentIdList()) {
            Content content = contentRepository.findById(contentId).orElseThrow(
                    () -> {
                        return new ContentException(ContentErrorCode.CONTENT_NOT_FOUND);
                    }
            );
            LiveContent liveContent = LiveContent.builder().content(content).build();
            liveContentList.add(liveContent);
        }
        Live live = Live.builder()
                .member(member)
                .title(liveCreateRequest.getTitle())
                .describe(liveCreateRequest.getDescribe())
                .guestList(guestList)
                .liveContentList(liveContentList)
                .build();

        liveRepository.save(live);

        openviduService.createSessionById(live.getId());

        OpenViduJoinSessionResponse openViduJoinSessionResponse = openviduService.joinSessionByIdAndRole(live.getId(), "PUBLISHER");

        return LiveJoinResponse.toDto(live,openViduJoinSessionResponse,"OWNER");

        //TODO : httpRequest 날려서 가져오기.
        //https://openvidu.beatherb.store/openvidu/api/sessions


    }

    @Transactional
    public LiveJoinResponse joinLive(MemberDTO memberDTO, LiveJoinRequest liveJoinRequest) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        Live live = liveRepository.findById(liveJoinRequest.getId()).orElseThrow(
                () -> {
                    return new LiveException(LiveErrorCode.LIVE_IS_NOT_EXIST);
                }
        );
        //TODO 리팩토링 필요. for 문 돌지 말고
        String OpenVidurole = "SUBSCRIBER";
        String role = "SUBSCRIBER";
        if (live.getMember() == member) {
            OpenVidurole = "PUBLISHER";
            role = "OWNER";
        } else if(guestRepository.findByMemberAndLive(member, live).isPresent()){
                OpenVidurole = "PUBLISHER";
                role = "GUEST";

        }
        OpenViduJoinSessionResponse openViduJoinSessionResponse = openviduService.joinSessionByIdAndRole(live.getId(), OpenVidurole);

        return LiveJoinResponse.toDto(live,openViduJoinSessionResponse,role);

    }


    public void deleteLive(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        Live live = liveRepository.findByMember(member).orElseThrow(
                () -> {
                    return new LiveException(LiveErrorCode.LIVE_IS_NOT_EXIST);
                }
        );

        openviduService.deleteSessionById(live.getId());
        liveRepository.delete(live);


    }

    public List<LiveDetailResponse> liveList(){
        List<Live> liveList = liveRepository.findAll();
        List<LiveDetailResponse> response = new ArrayList<>();

        for(Live live : liveList){
            Member member = live.getMember();
            List<LiveContentResponse> contentList = new ArrayList<>();
            for(LiveContent liveContent : live.getLiveContentList()){
                Content content = liveContent.getContent();

                contentList.add(LiveContentResponse.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .build());
            }

            response.add(LiveDetailResponse.builder()
                    .id(live.getId())
                    .title(live.getTitle())
                    .contentList(contentList)
                    .member(MasterMemberResponse.builder()
                                    .id(member.getId())
                                    .name(member.getName())
                                    .build())
                    .build());
        }

        return response;
    }
}
