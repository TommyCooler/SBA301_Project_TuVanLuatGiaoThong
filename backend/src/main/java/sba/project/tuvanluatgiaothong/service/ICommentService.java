package sba.project.tuvanluatgiaothong.service;

import org.springframework.data.domain.Page;

import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.pojo.Comment;

import java.util.UUID;

public interface ICommentService {
    Page<CommentResponse> getAllComments(int page, int size);

    Comment createComment(CommentCreateRequest commentCreateRequest);

    Comment updateComment(CommentUpdateRequest commentCreateRequest);

    void deleteComment(UUID commentId);
}
