package com.bsvcode.dtscatolog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bsvcode.dtscatolog.entities.Product;
import com.bsvcode.dtscatolog.repositories.factory.ProductRespositoryFactory;

@DataJpaTest
public class ProductRepositoryTest {
  @Autowired
  private ProductRepository repository;

  private long existingId;
  private long nonExsitingId;
  private Long countTotalProducts;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExsitingId = 1000L;
    countTotalProducts = 25L;
  }

  @Test
  public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
    Product product = ProductRespositoryFactory.createProduct();
    product.setId(null);
    product = repository.save(product);

    Assertions.assertNotNull(product.getId());
    Assertions.assertEquals(countTotalProducts + 1, product.getId());
  }

  @Test
  public void finByIdShouldReturnObjectWhenIdExists() {
    Optional<Product> product = repository.findById(existingId);
    Assertions.assertTrue(product.isPresent());
  }

  @Test
  public void findByIdShouldNotReturnObjectWhenIdIsNotExists() {
    Optional<Product> product = repository.findById(nonExsitingId);
    Assertions.assertTrue(product.isEmpty());
  }

  @Test
  public void deleteShouldDeleteObjectWhenIdExists() {
    repository.deleteById(existingId);
    Optional<Product> result = repository.findById(existingId);
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

    repository.findById(nonExsitingId);

    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
      repository.deleteById(nonExsitingId);
    });
  }

}
