package sba.project.tuvanluatgiaothong.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.mapper.CommentMapper;
import sba.project.tuvanluatgiaothong.pojo.Comment;
import sba.project.tuvanluatgiaothong.repository.CommentRepository;
import sba.project.tuvanluatgiaothong.repository.ICommentTransaction;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final ICommentTransaction transactionComment;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public ApiResponse<List<CommentResponse>> getAllComments(int page, int size) {
        return ApiResponse.<List<CommentResponse>>builder()
                .status("success")
                .message("")
                .dataResponse(commentRepository.findAll().stream().map(
                        this.commentMapper::toResponse
                ).toList())
                .build();
    }

    @Override
    public ApiResponse<CommentResponse> createComment(CommentCreateRequest commentCreateRequest) {
        try {
            var existingComment = commentRepository.findByUsername(commentCreateRequest.getUsername());
            if (existingComment.isPresent()) {
                return ApiResponse.<CommentResponse>builder()
                        .status("fail")
                        .message("Bạn đã thực hiện đánh giá trước đó!")
                        .dataResponse(null)
                        .build();
            }
            else {
                Comment savedComment = this.transactionComment.save(commentMapper.toEntity(commentCreateRequest));
                return ApiResponse.<CommentResponse>builder()
                        .status("success")
                        .message("")
                        .dataResponse(this.commentMapper.toResponse(savedComment))
                        .build();
            }
        }
        catch (Exception e) {
            throw new CustomExceptions.InternalServerException("Cannot save comment: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<CommentResponse> updateComment(CommentUpdateRequest request) {
        try {
            Comment comment = commentRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

            commentMapper.updateEntity(request, comment);
            return ApiResponse.<CommentResponse>builder()
                    .status("success")
                    .message("")
                    .dataResponse(this.commentMapper.toResponse(this.transactionComment.save(comment)))
                    .build();
        }
        catch (Exception e) {
            throw new CustomExceptions.InternalServerException("Cannot update comment: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> deleteComment(UUID commentId) {
        try {
            if (!commentRepository.existsById(commentId)) {
                throw new RuntimeException("Comment not found for deletion");
            }
            transactionComment.delete(commentId);
            return ApiResponse.builder()
                    .status("success")
                    .message("Deleted comment successfully!")
                    .build();
        }
        catch (Exception e) {
            throw  new CustomExceptions.InternalServerException("Cannot delete comment: " + e.getMessage());
        }
    }

}
