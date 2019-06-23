package com.myretail.product;

import com.myretail.product.domain.*;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.repository.ProductPriceRepository;
import com.myretail.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductPriceRepository productPriceRepository;

    @Mock
    private RestTemplate restTemplate;

    private ProductService productService;

    @Before
    public void setup() throws Exception {
        productService = new ProductService(productPriceRepository, restTemplate);
    }

    @Test
    public void getProductShouldReturnProduct() {

        given(productPriceRepository.findByProductId(anyString())).willReturn(new Price("1", "112233", 23.45d, "USD"));

        Description mockDescription = new Description();
        mockDescription.setTitle("The Big Lebowski (Blu-ray) (Widescreen)");
        Item mockItem = new Item();
        mockItem.setProductDescription(mockDescription);
        AvailableToPromiseNetwork availableToPromiseNetwork = new AvailableToPromiseNetwork();
        availableToPromiseNetwork.setProductId("112233");
        Product mockProduct = new Product();
        mockProduct.setId("1");
        mockProduct.setItem(mockItem);
        mockProduct.setAvailableToPromiseNetwork(availableToPromiseNetwork);

        Wrapper mockResponse = new Wrapper(mockProduct);

        given(restTemplate.getForObject(anyString(), any())).willReturn(mockResponse);

        Product product = productService.getProductById("1");

        assertThat(product.getId()).isEqualTo("112233");
        assertThat(product.getName()).isEqualTo("The Big Lebowski (Blu-ray) (Widescreen)");
        assertThat(product.getPrice().getId()).isEqualTo("1");
        assertThat(product.getPrice().getCurrencyCode()).isEqualTo("USD");
        assertThat(product.getPrice().getValue()).isEqualTo(23.45d);
        assertThat(product.getPrice().getProductId()).isEqualTo("112233");

    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductNotFound() throws Exception {
        given(restTemplate.getForObject(anyString(), any())).willThrow(ProductNotFoundException.class);

        Product product = productService.getProductById("1");
    }


}
