package sba.project.tuvanluatgiaothong.controller;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IAwsS3BucketService;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/aws/s3")
public class AwsS3Controller {

    private final IAwsS3BucketService awsS3BucketService;
    private final Environment environment;

    public AwsS3Controller(IAwsS3BucketService awsS3BucketService, Environment environment) {
        this.awsS3BucketService = awsS3BucketService;
        this.environment = environment;
    }

    @GetMapping("/health")
    public String health() {
        Map<String, String> config = new HashMap<>();
        config.put("maxFileSize", environment.getProperty("spring.servlet.multipart.max-file-size"));
        config.put("maxRequestSize", environment.getProperty("spring.servlet.multipart.max-request-size"));
        config.put("enabled", environment.getProperty("spring.servlet.multipart.enabled"));

        System.out.println(config);
        return "AWS S3 Bucket Service is up and running";
    }

    @PostMapping("/create-folder")
    public ResponseEntity<ApiResponse<?>> createFolder(@RequestParam("folderName") String folderName) {
        ApiResponse<?> response = awsS3BucketService.createFolder(folderName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName
    ) {
        ApiResponse<?> response = awsS3BucketService.uploadFile(file, folderName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
