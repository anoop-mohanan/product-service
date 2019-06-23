package com.myretail.product.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    String id;
    String productId;
    Double value;
    String currencyCode;
}
