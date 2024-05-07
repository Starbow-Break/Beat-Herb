package store.beatherb.restapi.global.validate;

import org.springframework.web.multipart.MultipartFile;

public class PictureValid {

    private static final String[] ALLOWED_MUSIC_EXTENSIONS = {"jpg", "jpeg", "png"};

    public static boolean isPictureFile(MultipartFile file) {
        // 파일 확장자 확인
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String fileExtension = getFileExtension(originalFilename);
            if (isValidMusicExtension(fileExtension)) {
                // 여기에 추가적인 검사 로직을 추가할 수도 있습니다.
                return true;
            }
        }
        return false;
    }

    private static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return ""; // 확장자가 없는 경우
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    private static boolean isValidMusicExtension(String extension) {
        for (String allowedExtension : ALLOWED_MUSIC_EXTENSIONS) {
            if (allowedExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
