package project.travelmate.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.utils.storage.S3Util;
import project.travelmate.utils.storage.StorageUtil;

@RestController
@RequiredArgsConstructor
public class S3TestController {

    //    private final StorageUtil storageUtil;
    private final S3Util s3Util;

    @PostMapping("/upload")
    public String uploadTest(@RequestPart(value = "image", required = false) MultipartFile file) {
        System.out.println("file = ");
        return s3Util.saveImage(file);
    }

}
