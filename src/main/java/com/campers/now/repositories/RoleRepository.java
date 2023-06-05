package com.campers.now.repositories;

import com.campers.now.models.Role;
import com.campers.now.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    int countByName(RoleType name);

    Role findByName(RoleType type);
}
