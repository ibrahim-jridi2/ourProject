package com.campers.now.controllers;

import com.campers.now.models.Product;
import com.campers.now.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Management")
@RestController
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product update(@RequestBody Product product, @PathVariable("id") Integer id) {
        product.setId(id);
        return productService.update(product);
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") Integer id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<Product> getAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "dir", required = false) String dir) {

        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return productService.getAll(page, sort, sortDir);
    }
}
