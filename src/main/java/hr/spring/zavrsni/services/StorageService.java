package hr.spring.zavrsni.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class StorageService {
    
    @Value("${application.bucket.name}")
    private String bucketName;

    
    private AmazonS3 s3;

    
    public String saveFile(MultipartFile file){
        String name=file.getOriginalFilename();
        File file1;
        try {
            file1 = convertMultiPartToFile(file);
            PutObjectResult result=s3.putObject(bucketName, name, file1);
            return result.getContentMd5();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    
    public byte[] download(String filename){
        try {
            S3Object object = s3.getObject(bucketName, filename);
            S3ObjectInputStream objectContent=object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException{
        File convFile=new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
