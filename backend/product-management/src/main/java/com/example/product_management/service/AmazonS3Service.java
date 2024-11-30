// package com.example.product_management.service;

// import com.example.product_management.model.AwsProperties;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import software.amazon.awssdk.core.sync.RequestBody;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
// import software.amazon.awssdk.services.s3.model.GetObjectRequest;
// import software.amazon.awssdk.services.s3.model.PutObjectRequest;
// import software.amazon.awssdk.services.s3.model.StorageClass;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;

// @Slf4j
// @Service
// public class AmazonS3Service {

//     private final S3Client s3Client;
//     private final AwsProperties awsProperties;

//     public AmazonS3Service(@Qualifier("s3Client") S3Client s3Client, AwsProperties awsProperties) {
//         this.s3Client = s3Client;
//         this.awsProperties = awsProperties;
//     }

//     public String uploadFile(MultipartFile multipartFile, Integer userId) {
//         try {
//             String bucketName = awsProperties.getS3().getBucket();
//             String fileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
//             String fileName = "user_" + userId + fileExtension;

//             PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                     .bucket(bucketName)
//                     .key(fileName)
//                     .contentLength(multipartFile.getSize())
//                     .storageClass(StorageClass.STANDARD)
//                     .build();

//             s3Client.putObject(putObjectRequest,
//                     RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));

//             return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
//         } catch (IOException e) {
//             return e.getMessage();
//         }
//     }

//     public void deleteFile(String fileName) {
//         DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                 .bucket(awsProperties.getS3().getBucket())
//                 .key(fileName)
//                 .build();

//         s3Client.deleteObject(deleteObjectRequest);
//     }

//     public String updateFile(String fileName, MultipartFile multipartFile) {
//         try {
//             PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                     .bucket(awsProperties.getS3().getBucket())
//                     .key(fileName)
//                     .contentLength(multipartFile.getSize())
//                     .storageClass(StorageClass.GLACIER)
//                     .build();

//             s3Client.putObject(putObjectRequest,
//                     RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));
//             return multipartFile.getOriginalFilename() + " Uploaded.";
//         } catch (IOException e) {
//             return e.getMessage();
//         }
//     }

//     public String downloadFile(String fileName) {
//         GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                 .bucket(awsProperties.getS3().getBucket())
//                 .key(fileName)
//                 .build();

//         try {
//             byte[] objectResponse = s3Client.getObject(getObjectRequest).readAllBytes();
//             File file = new File(System.getProperty("user.dir") + "/" + fileName);
//             FileOutputStream fileOutputStream = new FileOutputStream(file, false);
//             fileOutputStream.write(objectResponse);
//             fileOutputStream.close();
//             return file.getAbsolutePath();
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//     }




// }

package com.example.product_management.service;

import com.example.product_management.model.AwsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.StorageClass;

import java.io.IOException;

@Slf4j
@Service
public class AmazonS3Service {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    public AmazonS3Service(@Qualifier("s3Client") S3Client s3Client, AwsProperties awsProperties) {
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
    }

    public String uploadFile(MultipartFile multipartFile, Integer userId) {
        try {
            System.out.println("Uploading file to S3...");
            String bucketName = awsProperties.getS3().getBucket();
            String fileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
            String fileName = "category_" + userId + fileExtension;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentLength(multipartFile.getSize())
                    .storageClass(StorageClass.STANDARD)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));

            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}