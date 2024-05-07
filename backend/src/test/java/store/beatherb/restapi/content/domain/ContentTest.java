package store.beatherb.restapi.content.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    HashTagRepository hashTagRepository;

    @Autowired
    CreatorRepository creatorRepository;

    @Test
    void Content삽입(){
//        Member writer = Member.builder()
//                .dmAgree(true)
//                .name("엄준식")
//                .email("nanayago@da.co")
//                .nickname("용산")
//                .build();
//
//        memberRepository.save(writer);
//
//
//
//
//        HashTag hashTag = HashTag.builder()
//                .name("밥줘")
//                .build();
//
//        List<HashTag> hashTags = new ArrayList<>();
//        hashTags.add(hashTag);
//
//        hashTagRepository.save(hashTag);
//
//        Creator creator = Creator.builder()
//                .creator(writer)
//                .agree(false)
//                .build();
//
//        List<Creator> creators = new ArrayList<>();
//        creators.add(creator);
//        Content content = Content.builder()
//                .hit(0)
//                .lyrics("엄준식")
//                .describe("용기")
//                .title("이거 안넣었네")
//                .writer(writer)
//                .hashTags(hashTags)
//                .creators(creators)
//                .build();
////        creatorRepository.save(creator);
////        creator.setContent(content);
//        contentRepository.save(content);
////        creatorRepository.save(creator);
//
//        assertNotNull(creator.getId());
//        assertNotNull(hashTag.getId());
//        assertNotNull(creator.getId());
//        assertNotNull(content.getId());
    }
}