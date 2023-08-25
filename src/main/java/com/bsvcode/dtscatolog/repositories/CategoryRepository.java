package com.bsvcode.dtscatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsvcode.dtscatolog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
