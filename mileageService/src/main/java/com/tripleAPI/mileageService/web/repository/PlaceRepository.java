package com.tripleAPI.mileageService.web.repository;

import com.tripleAPI.mileageService.web.domain.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class PlaceRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Place place) {
        em.persist(place);
    }

    public Place findOne(UUID id) {
        return em.find(Place.class, id);
    }

    public List<Place> findAll() {
        return em.createQuery("select m from Place m", Place.class)
                .getResultList();
    }
}
