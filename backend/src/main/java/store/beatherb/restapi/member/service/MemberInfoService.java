package store.beatherb.restapi.member.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.content.domain.HashTagRepository;
import store.beatherb.restapi.content.exception.HashTagErrorCode;
import store.beatherb.restapi.content.exception.HashTagException;
import store.beatherb.restapi.global.exception.BeatHerbErrorCode;
import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.validate.PictureValid;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.interest.domain.InterestRepository;
import store.beatherb.restapi.interest.exception.InterestErrorCode;
import store.beatherb.restapi.interest.exception.InterestException;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.dto.request.EditRequest;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MemberInfoService {

    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;
    private final HashTagRepository hashTagRepository;
    private final String IMG_DIRECTORY;


    public MemberInfoService(MemberRepository memberRepository,
                             InterestRepository interestRepository,
                             HashTagRepository hashTagRepository,
                             @Value("${resource.directory.profile.image}") String IMG_DIRECTORY) {
        this.memberRepository = memberRepository;
        this.interestRepository = interestRepository;
        this.hashTagRepository = hashTagRepository;
        this.IMG_DIRECTORY = IMG_DIRECTORY;
    }

    //회원 정보 수정
    @Transactional
    public void edit(MemberDTO memberDTO, EditRequest editRequest) {

        String nickname = editRequest.getNickname() != null ? editRequest.getNickname() : memberDTO.getNickname();
        Boolean isDmAgree = editRequest.getDmAgree() != null ? editRequest.getDmAgree() : memberDTO.getDmAgree();
        List<Long> interestList = editRequest.getHashTagIdList();

        Member member = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_FIND_ERROR));


        if (!interestList.isEmpty()) {
            for (Interest interest : member.getInterestList()) {
                interestRepository.delete(interestRepository.findById(interest.getId())
                        .orElseThrow(() -> new InterestException(InterestErrorCode.INTEREST_IS_NOT_EXIST)));
            }
            for (Long request : interestList) {
                HashTag hashTag = hashTagRepository.findById(request)
                        .orElseThrow(() -> new HashTagException(HashTagErrorCode.HASHTAG_IS_NOT_EXIST));

                interestRepository.save(Interest.builder().hashTag(hashTag).member(member).build());
            }
            //기존 가지고 있는 관심사를 모두 삭제

        }

//        if (nickname == null) {
//            throw new MemberException(MemberErrorCode.NICKNAME_IS_NOT_EXIST);
//        }

        member.setNickname(nickname);
        member.setDmAgree(isDmAgree);


        // 요청 시 이미지가 있는 경우에만, 이미지 저장
        if (editRequest.getPicture() != null) {
            if (!PictureValid.isPictureFile(editRequest.getPicture())) {
                throw new MemberException(MemberErrorCode.PROFILE_IMAGE_NOT_VALID);
            }


            MultipartFile picture = editRequest.getPicture();
            String fileName = picture.getOriginalFilename();
            String format = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

            String uuid = UUID.randomUUID().toString();

            String saveFileName = uuid + format;

            File file = new File(IMG_DIRECTORY + File.separator + saveFileName);

            try {
                FileCopyUtils.copy(picture.getBytes(), file);
                member.setImage(saveFileName);
            } catch (IOException e) {
                throw new BeatHerbException(BeatHerbErrorCode.INTERNAL_SERVER_ERROR);
            }

        }

        memberRepository.save(member);
    }

    //회원 정보 수정 - 소셜 로그인 연동


}
