
 package sba.project.tuvanluatgiaothong.repository;

import sba.project.tuvanluatgiaothong.pojo.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
 public interface LawRepository extends JpaRepository<Law, UUID> {

    // This class is currently empty, but it can be used to define methods for interacting with the Law entity.
    // For example, you could add methods to find laws by title, content, or other attributes.
    
    // Example method signature:
    // List<Law> findByTitleContaining(String title);
    
    // You can also define custom queries using JPA annotations like @Query if needed.
}