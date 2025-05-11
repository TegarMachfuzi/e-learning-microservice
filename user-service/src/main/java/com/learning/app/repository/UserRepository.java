package com.learning.app.repository;

import com.learning.app.model.User;
import com.learning.app.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
     boolean existsByEmail(String username);
    List<User> findAllByRoles_Name(RoleName roleName);
}
