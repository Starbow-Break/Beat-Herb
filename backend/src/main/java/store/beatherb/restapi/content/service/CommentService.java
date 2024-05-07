package store.beatherb.restapi.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import store.beatherb.restapi.content.domain.Comment;
import store.beatherb.restapi.content.domain.CommentRepository;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.ContentRepository;
import store.beatherb.restapi.content.dto.request.RegistCommentRequest;
import store.beatherb.restapi.content.exception.ContentException;
import store.beatherb.restapi.global.auth.domain.LoginUser;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;
import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.util.List;
import java.util.Optional;

import static store.beatherb.restapi.content.exception.ContentErrorCode.CONTENT_NOT_FOUND;
import static store.beatherb.restapi.content.exception.ContentErrorCode.LIST_HAS_NOT_COMMENT;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    public void registerComment(@LoginUser MemberDTO memberDto, RegistCommentRequest registCommentRequest) {
        //contentId에 해당하는 Content를 찾는다
        Content content = contentRepository.findById(registCommentRequest.getContentId())
                .orElseThrow(() -> new ContentException(CONTENT_NOT_FOUND));
        //memberId에 해당하는 Member를 찾는다.
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_FIND_ERROR));

        Comment comment = Comment.builder()
                .member(member)
                .content(content)
                .body(registCommentRequest.getBody())
                .build();

        commentRepository.save(comment);

    }

    public List<Comment> getComments(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentException(CONTENT_NOT_FOUND));

        List<Comment> commentList = commentRepository.findByContent(content)
                .orElseThrow(() -> new ContentException(LIST_HAS_NOT_COMMENT));

        return commentList;
    }
}
