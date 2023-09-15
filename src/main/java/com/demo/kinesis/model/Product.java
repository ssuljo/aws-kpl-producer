package com.demo.kinesis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code Product} class represents a product in an e-commerce system.
 * It encapsulates information about a product, including its code, name, and price.
 *
 * <p>Instances of this class can be used to store and retrieve product details of
 * the e-commerce application, allowing for easy management and display of product
 * information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String code;
    private String name;
    private double price;
}
