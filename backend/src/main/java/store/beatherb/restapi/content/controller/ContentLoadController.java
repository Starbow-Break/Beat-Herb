package store.beatherb.restapi.content.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.beatherb.restapi.content.service.ContentLoadService;

@RestController
@RequestMapping("/content")
@Slf4j
@RequiredArgsConstructor
public class ContentLoadController {

    private final ContentLoadService contentLoadService;

    @GetMapping("/load/{contentId}")
    public ResponseEntity<Resource> load(@PathVariable Integer contentId){

        Resource resource = contentLoadService.getFile(contentId);
        String fileExtension = contentLoadService.getFileExtension();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"loadfile" + fileExtension + "\"")
                .body(resource);

    }
}
