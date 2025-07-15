package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;

public interface IUserManagementService {

    ApiResponse<Object> getAllUsers();

    ApiResponse<Object> disableUser(String id);
}
