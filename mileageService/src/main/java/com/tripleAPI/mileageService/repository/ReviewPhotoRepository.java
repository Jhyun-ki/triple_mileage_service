package com.tripleAPI.mileageService.repository;

import com.tripleAPI.mileageService.domain.Review;
import com.tripleAPI.mileageService.domain.ReviewPhoto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewPhotoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(ReviewPhoto reviewPhoto) {
        em.persist(reviewPhoto);
    }

    public ReviewPhoto findOne(UUID id) {
        return em.find(ReviewPhoto.class, id);
    }

    public List<ReviewPhoto> findByReview(Review review) {
        return em.createQuery("select rp from ReviewPhoto rp where rp.review = :review", ReviewPhoto.class)
                .setParameter("review", review)
                .getResultList();
    }
}
