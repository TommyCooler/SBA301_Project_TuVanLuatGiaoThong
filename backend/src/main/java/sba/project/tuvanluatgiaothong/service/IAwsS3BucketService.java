package sba.project.tuvanluatgiaothong.service;

import org.springframework.web.multipart.MultipartFile;
import sba.project.tuvanluatgiaothong.dto.ApiResponse;

public interface IAwsS3BucketService {

    ApiResponse<Object> createFolder(String folderName);

    ApiResponse<Object> uploadFile(MultipartFile file, String folder);
}
