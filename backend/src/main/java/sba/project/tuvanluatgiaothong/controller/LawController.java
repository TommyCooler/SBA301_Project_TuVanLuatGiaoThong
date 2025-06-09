package sba.project.tuvanluatgiaothong.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sba.project.tuvanluatgiaothong.dto.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.LawRequestDto;
import sba.project.tuvanluatgiaothong.service.ILawService;

@RestController
@RequestMapping("law")
public class LawController {

    @Autowired
    private ILawService iUsecase;
    
    @GetMapping("'/health")
    public String healthCheck() {
        return "Law Service is running";
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createLaw(@RequestBody LawRequestDto lawRequestDto) {
        // Logic to create a law
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("Law created successfully")
                        .dataResponse(iUsecase.createLaw(lawRequestDto))
                        .build(),
                HttpStatus.OK
        );
        
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateLaw(@PathVariable UUID id,@RequestBody LawRequestDto lawRequestDto) {
        // Logic to update a law
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("Law updated successfully")
                        .dataResponse(iUsecase.update(id, lawRequestDto))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<?>> getLawById(@PathVariable UUID id) {
        // Logic to get a law by ID
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("Law retrieved successfully")
                        .dataResponse(iUsecase.getLawById(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<?>> getAllLaw() {
        // Logic to get all laws
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("All laws retrieved successfully")
                        .dataResponse(iUsecase.getAllLaw())
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLaw(@PathVariable UUID id) {
        // Logic to delete a law
        iUsecase.delete(id);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("Law deleted successfully")
                        .build(),
                HttpStatus.OK
        );
    }


}
