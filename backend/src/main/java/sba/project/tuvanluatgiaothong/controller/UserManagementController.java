package sba.project.tuvanluatgiaothong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IUserManagementService;


@RestController
@RequestMapping("/api/v1/admin/user-management")
public class UserManagementController {

    private final IUserManagementService userManagementService;

    public UserManagementController(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Object>> getAllUsers() {
        return new ResponseEntity<>(userManagementService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<ApiResponse<Object>> disableUser(@PathVariable("id") String id) {
        return new ResponseEntity<>(userManagementService.disableUser(id), HttpStatus.OK);
    }
}
