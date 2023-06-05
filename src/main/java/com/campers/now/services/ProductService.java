package com.campers.now.services;

import com.campers.now.models.Product;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    List<Product> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Product getById(Integer id);

    Product add(Product o);

    Product update(Product o);
}
