package com.bsvcode.dtscatolog.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.bsvcode.dtscatolog.dtos.ProductDTO;
import com.bsvcode.dtscatolog.repositories.factory.ProductRespositoryFactory;
import com.bsvcode.dtscatolog.services.ProductService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService service;

  private ProductDTO productDTO;
  private PageImpl<ProductDTO> page;

  @BeforeEach
  void setUp() throws Exception {
    productDTO = ProductRespositoryFactory.createProductDTO();
    page = new PageImpl<>(List.of(productDTO));
    when(service.findAllPaged(any())).thenReturn(page);
  }

  @Test
  public void findAllShouldReturnPage() throws Exception {
    mockMvc.perform(get("/products")).andExpect(status().isOk());

  }

}
