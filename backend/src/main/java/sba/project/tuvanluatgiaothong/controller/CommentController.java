package sba.project.tuvanluatgiaothong.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.ICommentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/law")
public class CommentController {

    private final ICommentService commentService;

    @GetMapping("/comment/get-all")
    public ResponseEntity<ApiResponse<?>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(this.commentService.getAllComments(page, size), HttpStatus.OK);
    }

    @PostMapping("/comment/create")
    public ResponseEntity<ApiResponse<?>> createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        return new ResponseEntity<>(this.commentService.createComment(commentCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/comment/update")
    public ResponseEntity<ApiResponse<?>> updateComment(@Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return new ResponseEntity<>(this.commentService.updateComment(commentUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteComment(@PathVariable UUID id) {
        this.commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
