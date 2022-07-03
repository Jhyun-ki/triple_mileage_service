package com.tripleAPI.mileageService.web.repository;

import com.tripleAPI.mileageService.web.domain.Member;
import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.domain.PointCancelDetail;
import com.tripleAPI.mileageService.web.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class PointCancelDetailRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(PointCancelDetail review) {
        em.persist(review);
    }

    public PointCancelDetail findOne(UUID id) {
        return em.find(PointCancelDetail.class, id);
    }

    public List<PointCancelDetail> findAll() {
        return em.createQuery("select pcd from PointCancelDetail pcd", PointCancelDetail.class)
                .getResultList();
    }
}
