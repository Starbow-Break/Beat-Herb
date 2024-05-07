package store.beatherb.restapi.content.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ContentUploadRequest {

    @NotNull(message = "제목은 꼭 필요합니다.")
    String title;
    @Builder.Default
    String lyrics = "가사가 없습니다.";
    @Builder.Default
    String describe = "설명이 없습니다.";
    @NotEmpty(message = "최소 한개 이상의 해시태그 아이디가 있어야 합니다.")
    Set<Long> hashTagIdList;
    Set<Long> creatorIdList;
    Set<Long> rootContentIdList;
    MultipartFile image;
    @NotNull(message = "음악은 필수 업로드 하셔야합니다.")
    MultipartFile music;
    @NotNull(message = "VOCAL,MELODY,SOUNDTRACK 중 하나가 필요합니다" )
    String type;

    public ContentUploadRequest(String title, String lyrics, String describe, Set<Long> hashTagIdList, Set<Long> creatorIdList,Set<Long> rootContentIdList, MultipartFile image, MultipartFile music,String type) {
        this.title = title;
        this.lyrics = lyrics;
        this.describe = describe;
        this.hashTagIdList = hashTagIdList;
        this.type = type;
        if(creatorIdList ==null){
            creatorIdList = new HashSet<>();
        }
        if(rootContentIdList == null){
            rootContentIdList = new HashSet<>();
        }
        this.creatorIdList = creatorIdList;
        this.rootContentIdList = rootContentIdList;
        this.image = image;
        this.music = music;
    }
}
