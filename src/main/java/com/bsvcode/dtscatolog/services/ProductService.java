package com.bsvcode.dtscatolog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.CategoryDTO;
import com.bsvcode.dtscatolog.dtos.ProductDTO;
import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.entities.Product;
import com.bsvcode.dtscatolog.repositories.CategoryRepository;
import com.bsvcode.dtscatolog.repositories.ProductRepository;
import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository repository;
  @Autowired
  private CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(Pageable pageable) {
    Page<Product> list = repository.findAll(pageable);
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
    copyToEntity(product, entity);
    return new ProductDTO(entity);
  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO product) {
    try {
      Product entity = repository.getReferenceById(id);
      copyToEntity(product, entity);

      return new ProductDTO(entity);
    } catch (EntityNotFoundException exception) {
      throw new ResourceNotFoundException("Id Not found");
    }
  }

  private void copyToEntity(ProductDTO product, Product entity) {
    entity.setName(product.getName());
    entity.setDescription(product.getDescription());
    entity.setDate(product.getDate());
    entity.setImgUrl(product.getImgUrl());
    entity.setPrice(product.getPrice());
    entity.getCategories().clear();
    for (CategoryDTO categoryDTO : product.getCategories()) {
      Category category = categoryRepository.getOne(categoryDTO.getId());
      entity.getCategories().add(category);
    }
    entity = repository.save(entity);
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
