package com.campers.now.repositories;

import com.campers.now.models.ProductCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCommandRepository extends JpaRepository<ProductCommand, Integer> {
    List<ProductCommand> findByCommandId(int commandId);
}
