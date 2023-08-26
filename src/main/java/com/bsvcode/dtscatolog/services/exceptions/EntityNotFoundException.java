package com.bsvcode.dtscatolog.services.exceptions;

public class EntityNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(String mensagem) {
    super(mensagem);
  }
}
