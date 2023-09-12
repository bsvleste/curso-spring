package com.bsvcode.dtscatolog.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsvcode.dtscatolog.dtos.UserDTO;
import com.bsvcode.dtscatolog.dtos.UserInsertDTO;
import com.bsvcode.dtscatolog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
  @Autowired
  private UserService service;

  @GetMapping
  public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
    Page<UserDTO> list = service.findAllPaged(pageable);
    return ResponseEntity.ok().body(list);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    UserDTO user = service.findById(id);
    return ResponseEntity.ok().body(user);
  }

  @PostMapping
  public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO user) {
    UserDTO newUserDTO = service.insert(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newUserDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(newUserDTO);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserDTO user) {
    user = service.update(id, user);
    return ResponseEntity.ok().body(user);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
