package com.bsvcode.dtscatolog.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.entities.Product;

public class CategoryDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private List<ProductDTO> products = new ArrayList<>();

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

  public CategoryDTO(Category entity, Set<Product> products) {
    this(entity);
    products.forEach(product -> this.products.add(new ProductDTO(product)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public List<ProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDTO> products) {
    this.products = products;
  }
}
