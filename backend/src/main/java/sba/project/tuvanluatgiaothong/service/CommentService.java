package sba.project.tuvanluatgiaothong.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.CommentCreateRequest;
import sba.project.tuvanluatgiaothong.dto.request.CommentUpdateRequest;
import sba.project.tuvanluatgiaothong.dto.response.CommentResponse;
import sba.project.tuvanluatgiaothong.mapper.CommentMapper;
import sba.project.tuvanluatgiaothong.pojo.Comment;
import sba.project.tuvanluatgiaothong.repository.CommentRepository;
import sba.project.tuvanluatgiaothong.repository.ITransactionComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final ITransactionComment transactionComment;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Page<CommentResponse> getAllComments(int page, int size) {
        Page<Comment> commentsPage = commentRepository.findAll(PageRequest.of(page, size));
        return commentsPage.map(commentMapper::toResponse);
    }

    @Override
    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        Comment savedComment = transactionComment.save(commentMapper.toCreateEntity(commentCreateRequest));
        return commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found after creation"));
    }

    @Override
    public Comment updateComment(CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        commentMapper.updateEntity(request, comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found for deletion");
        }
        transactionComment.delete(commentId);
    }

}
