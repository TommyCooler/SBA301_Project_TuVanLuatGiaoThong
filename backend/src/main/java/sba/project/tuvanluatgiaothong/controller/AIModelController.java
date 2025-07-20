package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.AIModelRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IAIModelService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-packages/ai-models")
public class AIModelController {

    private final IAIModelService iAIModelService;

    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse<?>> createAIModel(@RequestBody AIModelRequest aiModelRequest) {
        return new ResponseEntity<>(iAIModelService.createAIModel(aiModelRequest), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllAIModel() {
        return new ResponseEntity<>(iAIModelService.getAllAIModel(), HttpStatus.OK);
    }
}
