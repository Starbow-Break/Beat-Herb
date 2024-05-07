package store.beatherb.restapi.content.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.content.domain.HashTagRepository;
import store.beatherb.restapi.content.dto.request.ContentUploadRequest;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HashTagRepository hashTagRepository;

    @Autowired
    ContentService contentService;

    @Test
    void 컨텐츠가_저장되야_한다() {

        Member writer = Member.builder()
                .name("엄준식")
                .email("nanayagoon@daum.net")
                .dmAgree(true)
                .build();

        Member  first = Member.builder()
                .name("햄버거")
                .email("naon@daum.net")
                .dmAgree(true)
                .build();

        memberRepository.save(writer);
        memberRepository.save(first);

        HashTag hashTag = HashTag.builder().name("잔잔함").build();
        hashTagRepository.save(hashTag);

        MemberDTO memberDTO = MemberDTO.builder().id(writer.getId()).build();

        Set<Long> hashTagIds = new HashSet<>();
        hashTagIds.add(hashTag.getId());
        ContentUploadRequest contentUploadRequest = ContentUploadRequest.builder()
                .title("제목")
                .hashTagIdList(hashTagIds)
                .creatorIdList(new HashSet<>())
                .build();

        contentService.uploadContent(memberDTO,contentUploadRequest);


    }
}