package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.stereotype.Component;

import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.pojo.Comment;

@Component("commentMapper_LawService")
public class CommentMapper {
    public Comment toCreateEntity(CommentCreateRequest commentCreateRequest) {
        if (commentCreateRequest == null) return null;
        Comment comment = new Comment();
        comment.setEmail(commentCreateRequest.getEmail());
        comment.setContent(commentCreateRequest.getContent());
        comment.setRating(commentCreateRequest.getRating());
        return comment;
    }

    public void updateEntity(CommentUpdateRequest request, Comment comment) {
        if (request == null || comment == null) return;

        if (request.getContent() != null) {
            comment.setContent(request.getContent().trim());
        }

        comment.setRating(request.getRating());
    }

    public CommentResponse toResponse(Comment comment) {
        if (comment == null) return null;
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setEmail(comment.getEmail());
        response.setContent(comment.getContent());
        response.setRating(comment.getRating());
        response.setCreatedDate(comment.getCreatedDate());
        response.setUpdatedDate(comment.getUpdatedDate());
        return response;
    }
}
