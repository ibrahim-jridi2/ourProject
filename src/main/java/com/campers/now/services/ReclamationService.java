package com.campers.now.services;

import com.campers.now.models.Reclamation;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ReclamationService {
    List<Reclamation> getAll(Integer pageNumber, String property, Sort.Direction direction);

    Reclamation getById(Integer id);

    Reclamation add(Reclamation o);

    Reclamation update(Reclamation o);
}
