package com.campers.now.services.Impl;

import com.campers.now.models.Command;
import com.campers.now.models.ProductCommand;
import com.campers.now.repositories.CommandRepository;
import com.campers.now.services.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@AllArgsConstructor
@Service
public class CommandServiceImpl implements CommandService {
    private final CommandRepository commandRepository;

    @Override
    public List<Command> getAllCommands() {
        return  commandRepository.findAll();

    }

    @Override
    public Command getCommandById(int id) {
        Optional<Command> optionalCommand = commandRepository.findById(id);
        return optionalCommand.orElse(null);
    }

    @Override
    public Command createCommand(Command command) {
        List<ProductCommand> productCommands = command.getProductCommands();
        for (ProductCommand productCommand : productCommands) {
            productCommand.setCommand(command);
        }
        return commandRepository.save(command);
    }


    @Override
    public Command updateCommand(int id, Command command) {
        Optional<Command> optionalCommand = commandRepository.findById(id);
        if (optionalCommand.isPresent()) {
            Command existingCommand = optionalCommand.get();
            existingCommand.setConfirmed(command.isConfirmed());
            existingCommand.setCustomerFirstName(command.getCustomerFirstName());
            existingCommand.setCustomerLastName(command.getCustomerLastName());
            existingCommand.setPhoneNumber(command.getPhoneNumber());
            existingCommand.setCustomerEmail(command.getCustomerEmail());
            existingCommand.setShippingAddress(command.getShippingAddress());
            return commandRepository.save(existingCommand);
        }
        return null;
    }

    @Override
    public void deleteCommand(int id) {
        commandRepository.deleteById(id);
    }
}
