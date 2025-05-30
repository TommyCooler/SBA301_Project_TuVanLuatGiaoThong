package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sba.project.tuvanluatgiaothong.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by username
    // User findByUsername(String username);
    
    // Find user by email
    User findByEmail(String email);
    
    // Check if a user exists by username
    // boolean existsByUsername(String username);
    
    // Check if a user exists by email
    boolean existsByEmail(String email);

}
