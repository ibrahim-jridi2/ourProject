package com.campers.now.services.Impl;

import com.campers.now.models.Product;
import com.campers.now.repositories.ProductRepository;
import com.campers.now.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    @Override
    public List<Product> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return productRepository.findAll();
        return productRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Product add(Product o) {
        try {
            return productRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Product o) {
        getById(o.getId());
        try {
            return productRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
