package com.campers.now.services.Impl;

import com.campers.now.models.Reclamation;
import com.campers.now.repositories.ReclamationRepository;
import com.campers.now.services.ReclamationService;
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
public class ReclamationServiceImpl implements ReclamationService {
    ReclamationRepository reclamationRepository;

    @Override
    public List<Reclamation> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return reclamationRepository.findAll();
        return reclamationRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Reclamation getById(Integer id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Reclamation add(Reclamation o) {
        try {
            return reclamationRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reclamation update(Reclamation o) {
        getById(o.getId());
        try {
            return reclamationRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
