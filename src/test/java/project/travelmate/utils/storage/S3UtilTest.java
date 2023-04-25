package project.travelmate.utils.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.advice.exception.ContentTypeException;
import project.travelmate.advice.exception.EmptyFileException;

import java.io.FileInputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3UtilTest {
    public static final String FILE_PATH = "src/test/java/resources/test_image.jpg";
    @Mock AmazonS3 amazonS3;

    private S3Util s3Util;

    private MultipartFile JPEGImageFile;
    private MultipartFile nullTypeFile;
    private MultipartFile wordFile;

    @BeforeEach
    public void init() throws Exception {
        this.s3Util = new S3Util(amazonS3);

        this.JPEGImageFile = makeMultipartFile("image/jpeg");
        this.nullTypeFile = makeMultipartFile(null);
        this.wordFile = makeMultipartFile("word");

    }

    private static MockMultipartFile makeMultipartFile(String contentType) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
        return new MockMultipartFile("fileName", "originFileName.jpg", contentType, fileInputStream);
    }

    @Nested
    @DisplayName("이미지 업로드시")
    class uploadImage {

        @Test
        public void emptyImageFile() throws Exception {
            assertThatThrownBy(() -> s3Util.saveImage(null))
                    .isInstanceOf(EmptyFileException.class);
        }

        @Test
        public void notImageContentType() throws Exception{
            assertThatThrownBy(() -> s3Util.saveImage(nullTypeFile))
                    .isInstanceOf(ContentTypeException.class);

            assertThatThrownBy(() -> s3Util.saveImage(wordFile))
                    .isInstanceOf(ContentTypeException.class);
        }

        @Test
        public void fileExtensionTest() throws Exception{

            when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(null);
            when(amazonS3.getUrl(nullable(String.class), anyString())).thenReturn(new URL("https://www.abc"));

            s3Util.saveImage(JPEGImageFile);

            verify(amazonS3).getUrl(
                            nullable(String.class),
                            endsWith(StringUtils.getFilenameExtension(JPEGImageFile.getOriginalFilename()))
                    );
        }

        @Test
        void success() throws Exception {
            when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(null);
            when(amazonS3.getUrl(nullable(String.class), anyString())).thenReturn(new URL("https://www.abc"));

            s3Util.saveImage(JPEGImageFile);

            verify(amazonS3).putObject(any(PutObjectRequest.class));
            verify(amazonS3).getUrl(nullable(String.class), anyString());
        }

    }

    @Nested
    @DisplayName("이미지 삭제시")
    class deleteImage {
        @Test
        void removePrefix() throws Exception {
            String url = "https://www.abc";
            String remain = "remain_url";

            when(amazonS3.getUrl(nullable(String.class), anyString())).thenReturn(new URL(url));
            doNothing().when(amazonS3).deleteObject(nullable(String.class), anyString());

            s3Util.deleteImage(url + remain);

            verify(amazonS3).deleteObject(null, remain);
        }

        @Test
        void success() throws Exception {
            String url = "https://www.abc";
            when(amazonS3.getUrl(nullable(String.class), anyString())).thenReturn(new URL(url));
            doNothing().when(amazonS3).deleteObject(nullable(String.class), anyString());

            s3Util.deleteImage(url);

            verify(amazonS3).deleteObject(nullable(String.class), anyString());
            verify(amazonS3).deleteObject(nullable(String.class), anyString());
        }
    }

}