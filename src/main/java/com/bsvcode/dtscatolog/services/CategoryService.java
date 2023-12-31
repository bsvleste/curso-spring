package com.bsvcode.dtscatolog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.CategoryDTO;
import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.repositories.CategoryRepository;
import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAllPaged(Pageable pageable) {
    Page<Category> list = repository.findAll(pageable);
    return list.map(category -> new CategoryDTO(category));
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Optional<Category> optionalCategory = repository.findById(id);
    Category category = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new CategoryDTO(category, category.getProducts());
  }

  @Transactional
  public CategoryDTO insert(CategoryDTO category) {
    Category entity = new Category();
    entity.setName(category.getName());
    entity = repository.save(entity);
    return new CategoryDTO(entity);
  }

  @Transactional
  public CategoryDTO update(Long id, CategoryDTO category) {
    try {
      Category entity = repository.getOne(id);
      entity.setName(category.getName());
      entity = repository.save(entity);
      return new CategoryDTO(entity);
    } catch (EntityNotFoundException exception) {
      throw new ResourceNotFoundException("Id Not found");
    }
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id Not found" + id);
    } catch (DataIntegrityViolationException exception) {
      throw new DatabaseResourceNotFoundException("Integrity Violation");
    }
  }
}
