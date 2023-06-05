package com.campers.now.services;

import com.campers.now.models.Command;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommandService {
    List<Command> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Command getById(Integer id);

    Command add(Command o);

    Command update(Command o);
}
