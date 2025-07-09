package sba.project.tuvanluatgiaothong.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sba.project.tuvanluatgiaothong.dto.ApiResponse;
import sba.project.tuvanluatgiaothong.service.LawTypeService;

@RestController
@RequestMapping("lawtype")
public class LawTypeController {

    @Autowired
    private LawTypeService lawTypeService;



    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<?>>getAllLawTypes() {
        return new ResponseEntity<>(
            ApiResponse.<Object>builder()
                .status("success")
                .message("Retrieved all law types successfully")
                .dataResponse(lawTypeService.getAllLawTypes())
                .build(),
            HttpStatus.OK
        );
    }
}
