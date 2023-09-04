package com.bsvcode.dtscatolog.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bsvcode.dtscatolog.entities.Product;
import com.bsvcode.dtscatolog.repositories.factory.ProductRespositoryFactory;
import com.bsvcode.dtscatolog.services.ProductService;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductService service;
  @Mock
  private ProductRepository repository;

  private long existingId;
  private long nonExistingId;
  private PageImpl<Product> page;
  private Product product;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    product = ProductRespositoryFactory.createProduct();
    page = new PageImpl<>(List.of(product));

    Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
    Mockito.doNothing().when(repository).deleteById(existingId);
    Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(nonExistingId);
  }

  @Test
  public void findAllShouldShowallProducts() {

  }

  @Test
  public void deleteShouldDeleteObjectWhenIdExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(nonExistingId);
    });
    Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
  }

  @Test
  public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
    Assertions.assertDoesNotThrow(() -> {
      service.delete(existingId);
    });
    Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
  }
}
