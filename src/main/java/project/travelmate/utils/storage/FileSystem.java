package project.travelmate.utils.storage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Transactional
@Service
public interface FileSystem {
    public String saveImage(MultipartFile imageFile);

    public void deleteImage(String imageUrl);
}
