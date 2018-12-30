package com.shiro.repository;

import com.shiro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String > {
}
