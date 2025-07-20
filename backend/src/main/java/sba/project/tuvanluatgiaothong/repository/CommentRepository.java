package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sba.project.tuvanluatgiaothong.pojo.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Optional<Comment> findByUsername(String username);

    List<Comment> findByRating(int rating);

}
