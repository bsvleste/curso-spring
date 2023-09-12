package com.bsvcode.dtscatolog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
  private List<FieldMessage> errors = new ArrayList<>();

  public List<FieldMessage> getErrors() {
    return errors;
  }

  public void addError(String fieldName, String error) {
    errors.add(new FieldMessage(fieldName, error));
  }
}
