// package sba.project.tuvanluatgiaothong.repository;

// import java.util.UUID;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import sba.project.tuvanluatgiaothong.pojo.User;

// @Repository
// public interface UserRepository extends JpaRepository<User, UUID> {
    
//     // Find user by username
//     // User findByUsername(String username);
    
//     // Find user by email
//     User findByEmail(String email);
    
//     // Check if a user exists by username
//     // Boolean existsByUsername(String username);
    
//     // Check if a user exists by email
//     Boolean existsByEmail(String email);

// }


package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sba.project.tuvanluatgiaothong.pojo.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAuth(String usernameAuth);

    Optional<User> findByEmail(String email);

    Boolean existsByUsernameAuth(String usernameAuth);
    Boolean existsByEmail(String email);
}
