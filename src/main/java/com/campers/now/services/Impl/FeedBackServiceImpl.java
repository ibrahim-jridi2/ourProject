package com.campers.now.services.Impl;

import com.campers.now.models.FeedBack;
import com.campers.now.repositories.FeedBackRepository;
import com.campers.now.services.FeedBackService;
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
public class FeedBackServiceImpl implements FeedBackService {
    FeedBackRepository feedbackRepository;

    @Override
    public List<FeedBack> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return feedbackRepository.findAll();
        return feedbackRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public FeedBack getById(Integer id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public FeedBack add(FeedBack o) {
        try {
            return feedbackRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FeedBack update(FeedBack o) {
        getById(o.getId());
        try {
            return feedbackRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
