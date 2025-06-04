package sba.project.tuvanluatgiaothong.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sba.project.tuvanluatgiaothong.pojo.LawType;

public interface LawTypeRepository extends JpaRepository<LawType, UUID> {

    // This class is currently empty, but it can be used to define methods for interacting with the LawType entity.
    // For example, you could add methods to find law types by name or other attributes.
    
    // Example method signature:
    // List<LawType> findByNameContaining(String name);
    
    // You can also define custom queries using JPA annotations like @Query if needed.
}