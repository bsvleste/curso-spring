package com.bsvcode.dtscatolog.repositories.factory;

import java.time.Instant;

import com.bsvcode.dtscatolog.dtos.ProductDTO;
import com.bsvcode.dtscatolog.entities.Category;
import com.bsvcode.dtscatolog.entities.Product;

public class ProductRespositoryFactory {
  public static Product createProduct() {
    Product product = new Product(1L, "Phone", "Good phone", 800.00, "https://img.com.ig",
        Instant.parse("2023-09-04T00:00:00Z"));
    product.getCategories().add(new Category(2L, "Eletronics"));
    return product;
  }

  public static ProductDTO createProductDTO() {
    Product product = createProduct();
    return new ProductDTO(product, product.getCategories());
  }
}
