package com.bsvcode.dtscatolog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.ProductDTO;
import com.bsvcode.dtscatolog.entities.Product;
import com.bsvcode.dtscatolog.repositories.ProductRepository;
import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
    Page<Product> list = repository.findAll(pageRequest);
    return list.map(product -> new ProductDTO(product));
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Optional<Product> optionalProduct = repository.findById(id);
    Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new ProductDTO(product, product.getCategories());
  }

  @Transactional
  public ProductDTO insert(ProductDTO product) {
    Product entity = new Product();
    entity.setName(product.getName());
    entity = repository.save(entity);
    return new ProductDTO(entity);
  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO product) {
    try {
      Product entity = repository.getReferenceById(id);
      entity.setName(product.getName());
      entity = repository.save(entity);
      return new ProductDTO(entity);
    } catch (EntityNotFoundException exception) {
      throw new ResourceNotFoundException("Id Not found");
    }
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new ResourceNotFoundException("Id Not found");
    } catch (DataIntegrityViolationException exception) {
      throw new DatabaseResourceNotFoundException("Integrity Violation");
    }
  }
}
