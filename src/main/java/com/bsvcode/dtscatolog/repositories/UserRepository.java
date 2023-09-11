package com.bsvcode.dtscatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsvcode.dtscatolog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
