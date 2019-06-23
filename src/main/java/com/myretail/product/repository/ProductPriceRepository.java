package com.myretail.product.repository;

import com.myretail.product.domain.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends CrudRepository<Price, String> {

    public Price findByProductId(String productId);

}
