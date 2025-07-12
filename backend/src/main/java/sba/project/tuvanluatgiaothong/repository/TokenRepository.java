package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sba.project.tuvanluatgiaothong.pojo.Token;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t " +
           "INNER JOIN User u ON t.user.id = u.id " +
           "WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUsername(UUID userId);
}
