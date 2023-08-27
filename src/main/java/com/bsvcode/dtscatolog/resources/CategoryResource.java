package com.bsvcode.dtscatolog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsvcode.dtscatolog.dtos.CategoryDTO;
import com.bsvcode.dtscatolog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
  @Autowired
  private CategoryService service;

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> findAll() {
    List<CategoryDTO> list = service.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
    CategoryDTO category = service.findById(id);
    return ResponseEntity.ok().body(category);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO category) {
    category = service.insert(category);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(category.getId()).toUri();
    return ResponseEntity.created(uri).body(category);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO category) {
    category = service.update(id, category);
    return ResponseEntity.ok().body(category);
  }
}
