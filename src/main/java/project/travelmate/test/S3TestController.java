package project.travelmate.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.utils.storage.FileSystem;
import project.travelmate.utils.storage.S3Util;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class S3TestController {

    //    private final StorageUtil storageUtil;
    private final FileSystem fileSystem;

    @PostMapping("/upload")
    public String uploadTest(@RequestPart(value = "image", required = false) MultipartFile file) {
        System.out.println("file = ");
        return fileSystem.saveImage(file);
    }

    @PostMapping("/delete")
    public void deleteTest(@RequestBody Map<String, String> value) {
        String imageUrl = value.get("imageUrl");
        fileSystem.deleteImage(imageUrl);
    }

}
