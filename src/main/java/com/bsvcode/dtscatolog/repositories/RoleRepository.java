package com.bsvcode.dtscatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsvcode.dtscatolog.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
