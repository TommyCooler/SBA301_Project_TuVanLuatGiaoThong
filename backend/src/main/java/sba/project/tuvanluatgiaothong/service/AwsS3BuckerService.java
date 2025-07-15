package sba.project.tuvanluatgiaothong.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.FolderNotFoundException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:aws.properties")
public class AwsS3BuckerService implements IAwsS3BucketService {
    
    @Value("${aws.s3.endpoint-url}")
    private String endpointUrl;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    @Override
    public ApiResponse<?> createFolder(String folderName) {
        if (folderName == null || folderName.isEmpty()) {
            throw new FolderNotFoundException("Folder name is empty! Please provide folder name");
        }

        String folderKey = folderName.endsWith("/") ? folderName : folderName + "/";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folderKey)
                .build();

        s3Client.putObject(request, RequestBody.empty());

        String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, folderKey);
        return new ApiResponse<>("success", "Folder created successfully", Optional.of(url));
    }

    @Override
    public ApiResponse<?> uploadFile(MultipartFile file, String folder) {
        String normalizedFolder = (folder == null || folder.isBlank())
                ? "uploads/"
                : (folder.endsWith("/") ? folder : folder + "/");

        String fileName = normalizedFolder + System.currentTimeMillis() + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
            return new ApiResponse<>("success", "File uploaded successfully", Optional.of(url));

        } catch (IOException | S3Exception e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    private String generatePresignedUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(60))
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
