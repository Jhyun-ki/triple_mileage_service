package com.tripleAPI.mileageService.web.repository;

import com.tripleAPI.mileageService.web.domain.Member;
import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.domain.Review;
import com.tripleAPI.mileageService.web.domain.ReviewPhoto;
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

    public List<ReviewPhoto> findAll() {
        return em.createQuery("select rp from ReviewPhoto rp", ReviewPhoto.class)
                .getResultList();
    }

    public List<ReviewPhoto> findByReview(Review review) {
        return em.createQuery("select rp from ReviewPhoto rp where rp.review = :review", ReviewPhoto.class)
                .setParameter("review", review)
                .getResultList();
    }
}
