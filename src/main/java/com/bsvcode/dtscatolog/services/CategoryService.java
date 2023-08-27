package com.bsvcode.dtscatolog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.CategoryDTO;
import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.repositories.CategoryRepository;
import com.bsvcode.dtscatolog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public List<CategoryDTO> findAll() {
    List<Category> list = repository.findAll();
    return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Optional<Category> optionalCategory = repository.findById(id);
    Category category = optionalCategory.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    return new CategoryDTO(category);
  }

  @Transactional
  public CategoryDTO insert(CategoryDTO category) {
    Category entity = new Category();
    entity.setName(category.getName());
    entity = repository.save(entity);
    return new CategoryDTO(entity);
  }

}
