package store.beatherb.restapi.directmessage.service;

import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageFetchRequest;
import store.beatherb.restapi.directmessage.domain.dto.request.DirectMessageSendRequest;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageFetchResponse;
import store.beatherb.restapi.directmessage.domain.dto.response.DirectMessageSendResponse;
import store.beatherb.restapi.member.dto.MemberDTO;

import java.util.List;

public interface DirectMessageService {

    DirectMessageSendResponse sendDirectMessage(MemberDTO senderDTO, DirectMessageSendRequest directMessageSendRequest) ;

    List<DirectMessageFetchResponse> findBySenderOrReceiverAndCreatedAtAfter(MemberDTO memberDTO, DirectMessageFetchRequest directMessageFetchRequest);
}
