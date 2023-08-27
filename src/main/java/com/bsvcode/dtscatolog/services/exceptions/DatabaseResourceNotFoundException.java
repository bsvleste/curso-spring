package com.bsvcode.dtscatolog.services.exceptions;

public class DatabaseResourceNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DatabaseResourceNotFoundException(String mensagem) {
    super(mensagem);
  }
}
