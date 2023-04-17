package project.travelmate.utils.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.advice.exception.CheckedWrapperException;
import project.travelmate.advice.exception.ContentTypeException;
import project.travelmate.advice.exception.EmptyFileException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static project.travelmate.advice.ExceptionCodeConst.*;

@RequiredArgsConstructor
@Service
public class S3Util implements FileSystem {
    public static final String IMAGE_JPEG_TYPE = "image/jpeg";
    public static final String IMAGE_PNG_TYPE = "image/png";

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String saveImage(MultipartFile imageFile) {
        validation(imageFile);

        return putObject(imageFile);
    }

    @Override
    public void deleteImage(String imageUrl) {
        amazonS3.deleteObject(bucket, imageUrl);
    }

    private void validation(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new EmptyFileException(EMPTY_FILE_CODE);
        }
        if (isNotImageContentType(imageFile.getContentType())) {
            throw new ContentTypeException(NOT_IMAGE_CONTENT_CODE);
        }
    }

    private boolean isNotImageContentType(String contentType) {
        if (contentType == null) return true;
        return !(contentType.contains(IMAGE_PNG_TYPE) || contentType.contains(IMAGE_JPEG_TYPE));
    }

    private String putObject(MultipartFile imageFile) {
        String fileName = makeUniqueFileName(imageFile);
        ByteArrayInputStream imageStream = imageToByteArrayStream(imageFile);
        ObjectMetadata metadata = contentMetaData(imageFile.getContentType());

        PutObjectRequest putRequest = new PutObjectRequest(bucket, fileName, imageStream, metadata);
        amazonS3.putObject(putRequest.withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String makeUniqueFileName(MultipartFile imageFile) {
        String extension = "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        String suffix = Long.toString(System.nanoTime());

        return imageFile.getName() + suffix + extension;
    }

    private ByteArrayInputStream imageToByteArrayStream(MultipartFile imageFile) {
        try {
            return new ByteArrayInputStream(IOUtils.toByteArray(imageFile.getInputStream()));
        } catch (IOException e) {
            throw new CheckedWrapperException(IO_EXCEPTION_CODE, e);
        }
    }

    private ObjectMetadata contentMetaData(String contentType) {
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentType(contentType);
        return metaData;
    }


}
