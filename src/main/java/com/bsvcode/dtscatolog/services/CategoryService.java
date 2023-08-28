package com.bsvcode.dtscatolog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.CategoryDTO;
import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.repositories.CategoryRepository;
import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
    Page<Category> list = repository.findAll(pageRequest);
    return list.map(category -> new CategoryDTO(category));
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Optional<Category> optionalCategory = repository.findById(id);
    Category category = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new CategoryDTO(category);
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
      Category entity = repository.getReferenceById(id);
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
    } catch (EmptyResultDataAccessException exception) {
      throw new ResourceNotFoundException("Id Not found");
    } catch (DataIntegrityViolationException exception) {
      throw new DatabaseResourceNotFoundException("Integrity Violation");
    }
  }
}
