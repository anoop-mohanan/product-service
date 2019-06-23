package com.myretail.product;

import com.myretail.product.domain.Price;
import com.myretail.product.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is an integration testing class.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setup() throws Exception {
        Price price = new Price();
        price.setProductId("13860428");
        price.setCurrencyCode("USD");
        price.setValue(24.45d);

        mongoTemplate.save(price);

        price = new Price();
        price.setProductId("15117729");
        price.setCurrencyCode("USD");
        price.setValue(1.45d);

        mongoTemplate.save(price);

        price = new Price();
        price.setProductId("16483589");
        price.setCurrencyCode("USD");
        price.setValue(3.05d);

        mongoTemplate.save(price);

        price = new Price();
        price.setProductId("16696652");
        price.setCurrencyCode("USD");
        price.setValue(30.45d);

        mongoTemplate.save(price);
    }



    @Test
    public void getProduct() {

        ResponseEntity<Product> response = restTemplate.getForEntity("/products/13860428", Product.class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo("13860428");
        assertThat(response.getBody().getName()).isEqualTo("The Big Lebowski (Blu-ray)");
        assertThat(response.getBody().getPrice().getCurrencyCode()).isEqualTo("USD");
        assertThat(response.getBody().getPrice().getValue()).isEqualTo(24.45d);

    }



}
