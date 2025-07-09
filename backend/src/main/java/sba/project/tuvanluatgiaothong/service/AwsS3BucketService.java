package sba.project.tuvanluatgiaothong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sba.project.tuvanluatgiaothong.dto.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.FolderNotFoundException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:aws.properties")
public class AwsS3BucketService implements IAwsS3BucketService {

    @Value("${aws.s3.endpoint-url}")
    private String endpointUrl;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public ApiResponse<Object> createFolder(String folderName) {
        if (folderName == null || folderName.isEmpty()) {
            throw new FolderNotFoundException("Folder name is empty! Please provide folder name");
        }

        String folderKey = folderName.endsWith("/") ? folderName : folderName + "/";
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folderKey)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.empty());

        String folderUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, folderKey);

        return new ApiResponse<>("success", "Folder created successfully", folderUrl);
    }

    @Override
    public ApiResponse<Object> uploadFile(MultipartFile file, String folder) {
        try {
            String normalizedFolder = (folder == null || folder.isBlank())
                    ? "uploads/"
                    : (folder.endsWith("/") ? folder : folder + "/");

            String fileName = normalizedFolder + System.currentTimeMillis() + "-" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);

            return new ApiResponse<>("success", "File uploaded successfully", fileUrl);

        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3", e);
        } catch (IOException e) {
            throw new RuntimeException("Uploading error", e);
        }
    }

    private String generatePresignedUrl(String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build())
                .signatureDuration(Duration.ofMinutes(60))
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}