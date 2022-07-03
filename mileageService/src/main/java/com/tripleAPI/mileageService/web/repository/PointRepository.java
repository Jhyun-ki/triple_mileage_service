package com.tripleAPI.mileageService.web.repository;

import com.tripleAPI.mileageService.web.domain.Member;
import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.domain.Point;
import com.tripleAPI.mileageService.web.domain.Review;
import com.tripleAPI.mileageService.web.domain.enums.PointState;
import com.tripleAPI.mileageService.web.domain.enums.PointType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class PointRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Point point) {
        em.persist(point);
    }

    public Point findOne(UUID id) {
        return em.find(Point.class, id);
    }

    public Point findByReviewIdAndPointType(Review review, PointType pointType, PointState pointState) {
        return (Point) em.createQuery("select p from Point p where p.review= : review and p.pointType= : pointType and p.pointState= :pointState")
                        .setParameter("review", review)
                        .setParameter("pointType", pointType)
                        .setParameter("pointState", pointState)
                        .getSingleResult();
    }

    public List<Point> findByReviewId(Review review, PointState pointState) {
        return em.createQuery("select p from Point p where p.review= :review and p.pointState = :pointState")
                .setParameter("review", review)
                .setParameter("pointState", pointState)
                .getResultList();
    }
}
