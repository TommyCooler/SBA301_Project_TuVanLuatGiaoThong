package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sba.project.tuvanluatgiaothong.pojo.AIModel;

import java.util.UUID;

@Repository
public interface AIModelRepository extends JpaRepository<AIModel, UUID> {
}
