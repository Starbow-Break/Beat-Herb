package store.beatherb.restapi.content.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.content.exception.ContentErrorCode;
import store.beatherb.restapi.content.exception.ContentException;

@Service
@Slf4j
public class ContentLoadService {

    private final ResourceLoader resourceLoader;
    @Getter
    private final String fileExtension;
    private final String RESOURCE_DIRECTORY_MUSIC;

    // id를 넘겨받으면 리소스 폴더에서 음원 파일을 찾아 response로 넘겨준다.
    // fileExtension은 .mp3로 고정
    public ContentLoadService(ResourceLoader resourceLoader, @Value("${resource.directory.music.convert}") String RESOURCE_DIRECTORY_MUSIC) {
        this.resourceLoader = resourceLoader;
        this.fileExtension = ".mp3";
        this.RESOURCE_DIRECTORY_MUSIC = RESOURCE_DIRECTORY_MUSIC;
    }

    public Resource getFile(Integer contentId) {
        String filePath = RESOURCE_DIRECTORY_MUSIC + "/" + contentId + fileExtension;
        Resource resource = resourceLoader.getResource("file:" + filePath);
        if (resource.exists()) {
            return resource;
        }
        throw new ContentException(ContentErrorCode.CONTENT_NOT_FOUND);
    }

}
