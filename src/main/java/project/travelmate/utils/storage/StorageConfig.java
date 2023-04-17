package project.travelmate.utils.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;


    @Bean
    public AmazonS3 amazonS3() { //amazonS3가 클라이언트 이다.
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        //accessKey와 secretKey를 이용하여 자격증명 객체를 얻습니다.

        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public FileSystem fileSystem() {
        return new S3Util(amazonS3());
    }

}
