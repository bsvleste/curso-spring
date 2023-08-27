package com.bsvcode.dtscatolog.dtos;

import java.io.Serializable;

import com.bsvcode.dtscatolog.entities.Category;

public class CategoryDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;

  public CategoryDTO() {
  }

  public CategoryDTO(Category entity) {
    this.id = entity.getId();
    this.name = entity.getName();
  }

  public CategoryDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}