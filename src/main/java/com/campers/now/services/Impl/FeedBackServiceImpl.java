package com.campers.now.services.Impl;

import com.campers.now.models.FeedBack;
import com.campers.now.repositories.FeedBackRepository;
import com.campers.now.services.FeedBackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            // If the campingCenter, product, or activity are not provided in the JSON body,
            // set them to null by default.
            if (o.getCampingCenter() == null) {
                o.setCampingCenter(null);
            }
            if (o.getProduct() == null) {
                o.setProduct(null);
            }
            if (o.getActivity() == null) {
                o.setActivity(null);
            }

            return feedbackRepository.save(o);
        } catch (Exception e) {
            log.debug(e.getMessage());
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
    @Override
    public List<FeedBack> getProductFeedbacks(Integer productId) {
        return feedbackRepository.findByProductId(productId);
    }

    @Override
    public List<FeedBack> getCampingCenterFeedbacks(Integer campingCenterId) {
        return feedbackRepository.findByCampingCenterId(campingCenterId);
    }

    @Override
    public List<FeedBack> getActivityFeedbacks(Integer activityId) {
        return feedbackRepository.findByActivityId(activityId);
    }

    @Override
    public double getRatingByUserId(Integer userId, Integer feedbackId) {
        FeedBack feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        if (feedback.getUser().getId().equals(userId)) {
            return feedback.getRating();
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public double getGeneralRatingByProductId(Integer productId) {
        List<FeedBack> feedbacks = feedbackRepository.findByProductId(productId);
        return calculateGeneralRating(feedbacks);
    }

    @Override
    public double getGeneralRatingByCampingCenterId(Integer campingCenterId) {
        List<FeedBack> feedbacks = feedbackRepository.findByCampingCenterId(campingCenterId);
        return calculateGeneralRating(feedbacks);
    }

    @Override
    public double getGeneralRatingByActivityId(Integer activityId) {
        List<FeedBack> feedbacks = feedbackRepository.findByActivityId(activityId);
        return calculateGeneralRating(feedbacks);
    }

    private double calculateGeneralRating(List<FeedBack> feedbacks) {
        int sum = feedbacks.stream()
                .mapToInt(FeedBack::getRating)
                .sum();

        int count = feedbacks.size();

        if (count > 0) {
            return (double) sum / count;
        } else {
            return 0.0;
        }
    }


}
