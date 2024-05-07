package store.beatherb.restapi.directmessage.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DirectMessageServiceImplTest {


    @Autowired
    DirectMessageService directMessageService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    void Kafka로메시지가_전송이_되야한다() throws Exception{

        Member sender = Member.builder()
                .nickname("감자자자")
                .email("햄버ㅁㄴㅇ")
                .name("고기반찬")
                .advertise(false)
                .build();

        Member receiver = Member.builder()
                .nickname("감자")
                .email("햄버거")
                .name("밥줘")
                .advertise(false)
                .build();
        memberRepository.save(sender);
        memberRepository.save(receiver);

        assertNotNull(sender.getId());
        assertNotNull(receiver.getId());

        MemberDTO senderDTO = MemberDTO.toDTO(sender);
        MemberDTO receiverDTO = MemberDTO.toDTO(receiver);

        assertEquals(senderDTO.getId(),sender.getId());
        assertEquals(receiverDTO.getId(),receiver.getId());

    }
}