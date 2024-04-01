package com.campers.now.services;

import com.campers.now.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Integer id);

    Product add(Product product);

    Product update(Integer id, Product updatedProduct);

    Product delete(Integer id);
}
