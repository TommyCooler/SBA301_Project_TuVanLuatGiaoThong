package sba.project.tuvanluatgiaothong.service;

import org.springframework.data.domain.Page;

import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.pojo.Comment;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    ApiResponse<List<CommentResponse>> getAllComments(int page, int size);

    ApiResponse<CommentResponse> createComment(CommentCreateRequest commentCreateRequest);

    ApiResponse<CommentResponse> updateComment(CommentUpdateRequest commentCreateRequest);

    ApiResponse<?> deleteComment(UUID commentId);
}
