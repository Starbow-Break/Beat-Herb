package store.beatherb.restapi.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.content.domain.ContentHashTag;
import store.beatherb.restapi.content.domain.ContentHashTagRepository;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.content.domain.HashTagRepository;
import store.beatherb.restapi.content.dto.request.DeleteHashTagRequest;
import store.beatherb.restapi.content.dto.request.HashTagSearchNameRequest;
import store.beatherb.restapi.content.dto.request.RegistHashTagRequest;
import store.beatherb.restapi.content.dto.request.UpdateHashTagRequest;
import store.beatherb.restapi.content.dto.response.HashTagListResponse;
import store.beatherb.restapi.content.dto.response.HashTagUseResponse;
import store.beatherb.restapi.content.dto.response.RegistHashTagResponse;
import store.beatherb.restapi.content.dto.response.UpdateHashTagResponse;
import store.beatherb.restapi.content.exception.HashTagErrorCode;
import store.beatherb.restapi.content.exception.HashTagException;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.interest.domain.InterestRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final ContentHashTagRepository contentHashTagRepository;
    private final InterestRepository interestRepository;

    public RegistHashTagResponse registHashTag(RegistHashTagRequest registHashTagRequest){
        HashTag hashTag = HashTag.builder()
                .name(registHashTagRequest.getName())
                .build();

        hashTagRepository.save(hashTag);

        return RegistHashTagResponse.toDto(hashTag);
    }

    public UpdateHashTagResponse updateHashTag(UpdateHashTagRequest updateHashTagRequest){
        HashTag hashTag = hashTagRepository.findById(updateHashTagRequest.getId())
                .orElseThrow(() -> new HashTagException(HashTagErrorCode.HASHTAG_IS_NOT_EXIST));

        hashTag.changeName(updateHashTagRequest.getName());

        hashTagRepository.save(hashTag);
        return UpdateHashTagResponse.toDto(hashTag);
    }

    public void deleteHashTag(DeleteHashTagRequest deleteHashTagRequest){
        hashTagRepository.delete(
                hashTagRepository.findById(deleteHashTagRequest.getId())
                        .orElseThrow(() -> new HashTagException(HashTagErrorCode.HASHTAG_IS_NOT_EXIST)));
    }

    public List<HashTagListResponse> findAllHashTag(){

        List<HashTag> list = hashTagRepository.findAll();
        List<HashTagListResponse> result = new ArrayList<>();
        for(HashTag t: list){
            result.add(HashTagListResponse.toDto(t));
        }
        return result;
    }

    public List<HashTagListResponse> findAllORfindByNameStartsWith(HashTagSearchNameRequest hashTagSearchNameRequest) {
        List<HashTag> list;
        if(hashTagSearchNameRequest.getName() == null){
            list = hashTagRepository.findAll();
        }
        else{
            list =  hashTagRepository.findByNameStartsWith(hashTagSearchNameRequest.getName());
        }
        List<HashTagListResponse> result = new ArrayList<>();
        for(HashTag t: list){
            result.add(HashTagListResponse.toDto(t));
        }
        return result;
    }

    public HashTagUseResponse findMemberAndContentByUsingHashTagId(Long id) {
        HashTag hashTag = hashTagRepository.findById(id).orElseThrow(
                () -> {
                    return new HashTagException(HashTagErrorCode.HASHTAG_IS_NOT_EXIST);
                }
        );
        List<ContentHashTag> contentHashTagList = contentHashTagRepository.findByHashTag(hashTag);
        //여기서 list 로 가져올것임.
        List<Interest> interestList  = interestRepository.findByHashTag(hashTag);

        return HashTagUseResponse.toDto(interestList,contentHashTagList);
    }
}
