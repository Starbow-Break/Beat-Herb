package store.beatherb.restapi.directmessage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.directmessage.domain.DirectMessage;
import store.beatherb.restapi.directmessage.domain.DirectMessageRepository;
import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageFetchRequest;
import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageSendRequest;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageFetchResponse;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageSendResponse;
import store.beatherb.restapi.infrastructure.kafka.domain.dto.response.DirectMessageToKafkaResponse;
import store.beatherb.restapi.directmessage.exception.DirectMessageErrorCode;
import store.beatherb.restapi.directmessage.exception.DirectMessageException;
import store.beatherb.restapi.infrastructure.kafka.service.KafkaProducerService;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectMessageServiceImpl implements DirectMessageService {

    private final MemberRepository memberRepository;
    private final DirectMessageRepository directMessageRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    @Override
    public DirectMessageSendResponse sendDirectMessage(MemberDTO senderDTO, DirectMessageSendRequest directMessageSendRequest) {

        Member sender = memberRepository.findById(senderDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );

        Member receiver = memberRepository.findById(directMessageSendRequest.getReceiverId()).orElseThrow(() -> {
            return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
        });
        if (sender == receiver) {
            throw new DirectMessageException(DirectMessageErrorCode.SENDER_EQUAL_RECEIVER);
        }
        if (!receiver.isDmAgree()) {
            throw new DirectMessageException(DirectMessageErrorCode.RECEIVER_NOT_ALLOW_DIRECT_MESSAGE);
        }

        DirectMessage directMessage = DirectMessage.builder()
                .sender(sender)
                .message(directMessageSendRequest.getMessage())
                .receiver(receiver)
                .build();

        directMessageRepository.saveAndFlush(directMessage);


        DirectMessageToKafkaResponse directMessageToKafkaResponse = DirectMessageToKafkaResponse.toDTO(directMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            String directMessageResponseJson = objectMapper.writeValueAsString(directMessageToKafkaResponse);
            kafkaProducerService.sendDirectMessage(directMessageResponseJson);

            return DirectMessageSendResponse.builder()
                    .createdAt(directMessage.getCreatedAt())
                    .build();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DirectMessageException(DirectMessageErrorCode.DIRECT_MESSAGE_SEND_FAIL);
        }


    }

    @Transactional
    @Override
    public List<DirectMessageFetchResponse> findBySenderOrReceiverAndCreatedAtAfter(MemberDTO memberDTO, DirectMessageFetchRequest directMessageFetchRequest) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        List<DirectMessage> directMessageList = directMessageRepository.findByCreatedAtAfterAndSenderOrReceiver(directMessageFetchRequest.getTime(),member,member);

        List<DirectMessageFetchResponse> directMessageFetchResponseList = new ArrayList<>();
        for(DirectMessage d: directMessageList){
            directMessageFetchResponseList.add(DirectMessageFetchResponse.toDto(d));
        }

        log.info("directMessageList = [{}]",directMessageList);
        return directMessageFetchResponseList;


    }


}
