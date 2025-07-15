package sba.project.tuvanluatgiaothong.service;

import org.springframework.web.multipart.MultipartFile;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;

public interface IAwsS3BucketService {

    ApiResponse<?> createFolder(String folderName);

    ApiResponse<?> uploadFile(MultipartFile file, String folder);
}
