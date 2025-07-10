package sba.project.tuvanluatgiaothong.repository;


import java.util.UUID;

import sba.project.tuvanluatgiaothong.pojo.Comment;

public interface ITransactionComment {
    Comment save(Comment comment);

    void update(Comment comment);

    void delete(UUID commentId);
}
