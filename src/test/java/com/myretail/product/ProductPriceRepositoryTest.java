package com.myretail.product;

import com.myretail.product.domain.Price;
import com.myretail.product.repository.ProductPriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductPriceRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Test
    public void getProductPrice() throws Exception {

        //Save a Price.
        mongoTemplate.save(new Price(null, "112233", 23.45d, "USD"));

        //Query the price.
        Price price = productPriceRepository.findByProductId("112233");


        //Assert the price.
        assertThat(price.getProductId()).isEqualTo("112233");
        assertThat(price.getValue()).isEqualTo(23.45d);
        assertThat(price.getCurrencyCode()).isEqualTo("USD");

    }


}
