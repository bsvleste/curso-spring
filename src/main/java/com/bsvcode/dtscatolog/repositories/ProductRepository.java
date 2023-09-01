package com.bsvcode.dtscatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsvcode.dtscatolog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
