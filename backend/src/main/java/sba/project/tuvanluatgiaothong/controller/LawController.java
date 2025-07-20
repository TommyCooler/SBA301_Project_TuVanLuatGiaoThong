package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.LawRequest;
import sba.project.tuvanluatgiaothong.dto.request.LawTypeRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.ILawService;
import sba.project.tuvanluatgiaothong.service.ILawTypeService;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/law")
public class LawController {

    private final ILawService iLawService;

    private final ILawTypeService iLawTypeService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Law Service is running";
    }

    @PostMapping("/admin/type/create")
    public ResponseEntity<ApiResponse<?>> createLawType(@RequestBody LawTypeRequest lawTypeRequest) {
        return new ResponseEntity<>(this.iLawTypeService.createLawType(lawTypeRequest), HttpStatus.OK);
    }

    @GetMapping("/type/get-all")
    public ResponseEntity<ApiResponse<?>> getAllLawTypes() {
        return new ResponseEntity<>(this.iLawTypeService.getAllLawTypes(), HttpStatus.OK);
    }

    @GetMapping("/type/get/{id}")
    public ResponseEntity<ApiResponse<?>> getLawTypeById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.iLawTypeService.getLawTypeById(id), HttpStatus.OK);
    }

    @PutMapping("/admin/type/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateLawType(@PathVariable("id") UUID id, @RequestBody LawTypeRequest lawTypeRequest) {
        return new ResponseEntity<>(this.iLawTypeService.updateLawTypes(id, lawTypeRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/type/deactivate/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateLawType(UUID id) {
        return new ResponseEntity<>(this.iLawTypeService.deactivateLawType(id), HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse<?>> createLaw(@RequestBody LawRequest lawRequest) {
        return new ResponseEntity<>(this.iLawService.createLaw(lawRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/deactivate/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateLaw(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.iLawService.deactivateLaw(id), HttpStatus.OK);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateLaw(@PathVariable UUID id,@RequestBody LawRequest lawRequest) {
        return new ResponseEntity<>(this.iLawService.updateLaw(id, lawRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<?>> getLawById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.iLawService.getLawById(id), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllLaw() {
        return new ResponseEntity<>(this.iLawService.getAllLaws(), HttpStatus.OK);
    }

    @PostMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLaw(@PathVariable UUID id) {
        return null;
    }


}
