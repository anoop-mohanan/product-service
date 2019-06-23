package com.myretail.product;

import com.myretail.product.domain.Product;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductShouldReturnProduct() throws Exception {

        given(productService.getProductById(anyString())).willReturn(new Product("1", "The Big Lebowski (Blu-ray) (Widescreen)"));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("The Big Lebowski (Blu-ray) (Widescreen)"));
    }

    @Test
    public void getProductNotFound() throws Exception {
        given(productService.getProductById(anyString())).willThrow(ProductNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/2"))
                .andExpect(status().isNotFound());
    }
}
