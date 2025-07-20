package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sba.project.tuvanluatgiaothong.pojo.TransactionHistory;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

     List<TransactionHistory> findByUserId(UUID userId);

}
