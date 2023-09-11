package com.bsvcode.dtscatolog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsvcode.dtscatolog.dtos.RoleDTO;
import com.bsvcode.dtscatolog.dtos.UserDTO;
import com.bsvcode.dtscatolog.dtos.UserInsertDTO;
import com.bsvcode.dtscatolog.entities.Role;
import com.bsvcode.dtscatolog.entities.User;
import com.bsvcode.dtscatolog.repositories.RoleRepository;
import com.bsvcode.dtscatolog.repositories.UserRepository;
import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository repository;
  @Autowired
  private RoleRepository roleRepository;

  @Transactional(readOnly = true)
  public Page<UserDTO> findAllPaged(Pageable pageable) {
    Page<User> list = repository.findAll(pageable);
    return list.map(user -> new UserDTO(user));
  }

  @Transactional(readOnly = true)
  public UserDTO findById(Long id) {
    Optional<User> optionalUser = repository.findById(id);
    User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new UserDTO(user);
  }

  @Transactional
  public UserDTO insert(UserInsertDTO user) {
    User entity = new User();
    copyToEntity(user, entity);
    entity.setPassword(passwordEncoder.encode(user.getPassword()));
    entity = repository.save(entity);
    return new UserDTO(entity);
  }

  @Transactional
  public UserDTO update(Long id, UserDTO user) {
    try {
      User entity = repository.getOne(id);
      copyToEntity(user, entity);

      entity = repository.save(entity);
      return new UserDTO(entity);
    } catch (EntityNotFoundException exception) {
      throw new ResourceNotFoundException("Id Not found" + id);
    }
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new ResourceNotFoundException("Id Not found" + id);
    } catch (DataIntegrityViolationException exception) {
      throw new DatabaseResourceNotFoundException("Integrity Violation");
    }
  }

  private void copyToEntity(UserDTO user, User entity) {
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setEmail(user.getEmail());
    entity.getRoles().clear();

    for (RoleDTO roleDTO : user.getRoles()) {
      Role role = roleRepository.getOne(roleDTO.getId());
      entity.getRoles().add(role);
    }

  }

}
