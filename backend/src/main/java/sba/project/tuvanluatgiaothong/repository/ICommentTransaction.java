package sba.project.tuvanluatgiaothong.repository;

import sba.project.tuvanluatgiaothong.pojo.Comment;

import java.util.UUID;

public interface ICommentTransaction {
    Comment save(Comment comment);

    void update(Comment comment);

    void delete(UUID commentId);
}
