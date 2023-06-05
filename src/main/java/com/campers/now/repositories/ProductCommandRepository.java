package com.campers.now.repositories;

import com.campers.now.models.ProductCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommandRepository extends JpaRepository<ProductCommand, Integer> {
}
