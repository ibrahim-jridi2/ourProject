    package com.campers.now.repositories;

    import com.campers.now.models.FeedBack;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
        List<FeedBack> findByProductId(Integer productId);

        List<FeedBack> findByCampingCenterId(Integer campingCenterId);

        List<FeedBack> findByActivityId(Integer activityId);
    }