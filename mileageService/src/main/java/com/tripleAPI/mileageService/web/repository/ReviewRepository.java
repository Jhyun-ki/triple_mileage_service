package com.tripleAPI.mileageService.web.repository;

import com.tripleAPI.mileageService.web.domain.Member;
import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.domain.Review;
import com.tripleAPI.mileageService.web.domain.enums.Action;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public Review findOne(UUID id) {
        return em.find(Review.class, id);
    }

    public List<Review> findByMemberAndPlace(Member member, Place place) {
        return em.createQuery("select r from Review r where r.member= :member and r.place= :place", Review.class)
                .setParameter("member", member)
                .setParameter("place", place)
                .getResultList();
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    public boolean isAddReviewAvailable(Member member, Place place) {
        return em.createQuery("select r from Review r where r.place= :place and r.member= :member")
                .setParameter("place", place)
                .setParameter("member", member)
                .setMaxResults(1)
                .getResultList().size() == 0 ? true : false ;
    }

    public boolean isFirstReview(Place place, Action action) {
         return em.createQuery("select r from Review r where r.place= :place and r.action <> :action")
                            .setParameter("place", place)
                            .setParameter("action", action)
                            .getResultList().size() == 1 ? true: false ;
    }
}
