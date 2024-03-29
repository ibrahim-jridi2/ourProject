package com.campers.now.services;

import com.campers.now.models.Command;

import java.util.List;

public interface CommandService {
    List<Command> getAllCommands();

    Command getCommandById(int id);

    Command createCommand(Command command);

    Command updateCommand(int id, Command command);

    void deleteCommand(int id);
}
