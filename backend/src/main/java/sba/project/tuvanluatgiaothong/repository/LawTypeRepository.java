package sba.project.tuvanluatgiaothong.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sba.project.tuvanluatgiaothong.pojo.LawType;


@Repository
public interface LawTypeRepository extends JpaRepository<LawType, UUID> {

}
