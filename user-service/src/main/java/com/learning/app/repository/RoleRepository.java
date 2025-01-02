package com.learning.app.repository;

import com.learning.app.model.Role;
import com.learning.app.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
