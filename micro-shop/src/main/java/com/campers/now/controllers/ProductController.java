package com.campers.now.controllers;

import com.campers.now.models.Product;
import com.campers.now.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Management")
@RestController
@AllArgsConstructor
@RequestMapping("products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @PostMapping

    public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @PutMapping("/{id}")

    public Product update(@PathVariable("id") Integer id, @RequestBody Product updatedProduct) {
        return productService.update(id, updatedProduct);
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") Integer id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }
    @DeleteMapping("/{id}")
    public Product delete(@PathVariable("id") Integer id) {
        return productService.delete(id);
    }
}
