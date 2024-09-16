package hr.spring.zavrsni.services;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private AmazonS3 s3;

    public String saveFile(File file) {
        try {
            PutObjectResult result = s3.putObject(bucketName,file.getAbsolutePath(), file);
            return result.getContentMd5();

        } catch (Exception e) {
            System.out.println(e);
            return "Greska spremanja datoteke";
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), null);
            PutObjectResult result = s3.putObject(putObjectRequest);
            return result.getContentMd5();

        } catch (Exception e) {
            System.out.println(e);
            return "Greska";
        }
    }

    public byte[] download(String filename) {
        try {
            S3Object object = s3.getObject(bucketName, filename);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}