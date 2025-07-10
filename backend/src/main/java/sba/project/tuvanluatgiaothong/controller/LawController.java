package sba.project.tuvanluatgiaothong.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.request.LawRequest;
import sba.project.tuvanluatgiaothong.dto.request.LawTypeRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.pojo.Comment;
import sba.project.tuvanluatgiaothong.service.ICommentService;
import sba.project.tuvanluatgiaothong.service.ILawService;
import sba.project.tuvanluatgiaothong.service.ILawTypeService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/law")
public class LawController {

    private final ILawService lawService;

    private final ILawTypeService lawTypeService;

    private final ICommentService commentService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Law Service is running";
    }

    @PostMapping("/admin/type/create")
    public ResponseEntity<ApiResponse<?>> createLawType(@RequestBody LawTypeRequest lawTypeRequest) {
        return new ResponseEntity<>(this.lawTypeService.createLawType(lawTypeRequest), HttpStatus.OK);
    }

    @GetMapping("/type/get-all")
    public ResponseEntity<ApiResponse<?>> getAllLawTypes() {
        return new ResponseEntity<>(this.lawTypeService.getAllLawTypes(), HttpStatus.OK);
    }

    @GetMapping("/type/get/{id}")
    public ResponseEntity<ApiResponse<?>> getLawTypeById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.lawTypeService.getLawTypeById(id), HttpStatus.OK);
    }

    @PutMapping("/admin/type/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateLawType(@PathVariable("id") UUID id, @RequestBody LawTypeRequest lawTypeRequest) {
        return new ResponseEntity<>(this.lawTypeService.updateLawTypes(id, lawTypeRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/type/deactivate/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateLawType(UUID id) {
        return new ResponseEntity<>(this.lawTypeService.deactivateLawType(id), HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse<?>> createLaw(@RequestBody LawRequest lawRequest) {
        return new ResponseEntity<>(this.lawService.createLaw(lawRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/deactivate/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateLaw(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.lawService.deactivateLaw(id), HttpStatus.OK);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateLaw(@PathVariable UUID id,@RequestBody LawRequest lawRequest) {
        return new ResponseEntity<>(this.lawService.updateLaw(id, lawRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<?>> getLawById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.lawService.getLawById(id), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllLaw() {
        return new ResponseEntity<>(this.lawService.getAllLaws(), HttpStatus.OK);
    }

    @PostMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLaw(@PathVariable UUID id) {
        return null;
    }

    @GetMapping("/comment/get-all")
    public ResponseEntity<Page<CommentResponse>> getAllComments(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(this.commentService.getAllComments(page, size), HttpStatus.OK);
    }

    @PostMapping("/comment/create")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        return new ResponseEntity<>(this.commentService.createComment(commentCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/comment/update")
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return new ResponseEntity<>(this.commentService.updateComment(commentUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        this.commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
