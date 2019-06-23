package com.myretail.product.service;

import com.myretail.product.domain.Price;
import com.myretail.product.domain.Product;
import com.myretail.product.domain.Wrapper;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.exception.ProductPriceNotFoundException;
import com.myretail.product.repository.ProductPriceRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Data
@Service
public class ProductService {

    //Class level logger.
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Value("${product.info.base.url}")
    private String baseUrl;

    @Value("${product.info.parameters}")
    private String parameters;

    public ProductService(ProductPriceRepository productPriceRepository, RestTemplate restTemplate) {
        this.productPriceRepository = productPriceRepository;
        this.restTemplate = restTemplate;
    }

    private ProductPriceRepository productPriceRepository;

    private RestTemplate restTemplate;

    /**
     * This method will return the product for the id passed in.
     * @param id - product id
     * @return - Product got the given id.
     */
    public Product getProductById(String  id) {

        LOGGER.info("getting product for id : {}", id);

        Wrapper response = getProductDetails(id);

        LOGGER.debug("retrieved product : {} for id {}", response.getProduct().getItem().getProductDescription(), response.getProduct().getAvailableToPromiseNetwork().getProductId());

        Price price = productPriceRepository.findByProductId(id);

        if(price == null) {
            LOGGER.error("No price found for the product with id : {}", id);
            throw new ProductPriceNotFoundException("Price for Product Id : " + id + " not found");
        }

        LOGGER.debug("retrieved price : [ value : {}, currencyCode : {}] for product Id : {}", price.getValue(), price.getCurrencyCode(), id);

        Product product = new Product();
        product.setPrice(price);
        product.setName(response.getProduct().getItem().getProductDescription().getTitle());
        product.setId(response.getProduct().getAvailableToPromiseNetwork().getProductId());

        return product;
    }

    /**
     * This method makes a call to the external service to retrieve the product details for the product id passed in.
     * @param id - product id.
     * @return - Response of the service call.
     */
    public Wrapper getProductDetails(String id) {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(String.valueOf(id));
        urlBuilder.append("?");
        urlBuilder.append(parameters);

        LOGGER.info("Calling restEndpoint: {} ", urlBuilder.toString());

        Wrapper response = null;
        try {
            response = restTemplate.getForObject(urlBuilder.toString(), Wrapper.class);
        } catch(HttpClientErrorException cheEx) {
            if(cheEx.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.warn("404 occurred while calling endpoint {}", urlBuilder.toString());
                throw new ProductNotFoundException("Product Id : " + id  + " not found");
            } else {
                LOGGER.error("Exception occurred while calling endpoing: {}", urlBuilder, cheEx);
                throw cheEx;
            }
        }

        if(response == null || response.getProduct() == null) {
            throw new ProductNotFoundException("Product Id : " + id + " not found.");
        }

        return response;
    }


    public Wrapper backingService() {
        //Call the alternate path. DB / alternate URL.
        Wrapper defaultWrapper = new Wrapper();
        LOGGER.debug("Inside backing service.");
        return defaultWrapper;
    }
}
