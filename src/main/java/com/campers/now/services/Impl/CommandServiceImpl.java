package com.campers.now.services.Impl;

import com.campers.now.models.Command;
import com.campers.now.repositories.CommandRepository;
import com.campers.now.repositories.ProductCommandRepository;
import com.campers.now.repositories.ProductRepository;
import com.campers.now.services.CommandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class CommandServiceImpl implements CommandService {
    CommandRepository commandRepository;
    ProductRepository productRepository;
    ProductCommandRepository productCommandRepository;

    @Override
    public List<Command> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return commandRepository.findAll();
        return commandRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Command getById(Integer id) {
        return commandRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Command add(Command o) {
        try {
            var prodComs = o.getProductCommands();

            prodComs.forEach(productCommand -> {
                var p = productRepository.findById(productCommand.getProduct().getId());
                p.ifPresent(product -> {
                    var priceAfterDiscount = product.getPrice() * (100 - product.getDiscount()) / 100;
                    var total = priceAfterDiscount * productCommand.getQuantity();
                    productCommand.setPriceTotal(total);
                });
            });
            o.setProductCommands(null);
            Command command = commandRepository.save(o);
            prodComs.forEach(productCommand -> productCommand.setCommand(command));
            var productCommands = productCommandRepository.saveAll(prodComs);
            command.setProductCommands(productCommands);
            return command;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Command update(Command o) {
        getById(o.getId());
        try {
            return commandRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
