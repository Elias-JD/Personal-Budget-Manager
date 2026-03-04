package com.elias.budgetmanager.repository;

import com.elias.budgetmanager.model.Role;
import com.elias.budgetmanager.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleName name);
}
