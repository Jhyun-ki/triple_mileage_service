package com.tripleAPI.mileageService.api.service;

import com.tripleAPI.mileageService.domain.Place;
import com.tripleAPI.mileageService.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //repository를 생성자 주입 해줌
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public UUID join(Place place) {
        placeRepository.save(place);
        return place.getId();
    }

    public List<Place> findPlaces() {
        return placeRepository.findAll();
    }

    public Place findOne(UUID placeId) {
        return placeRepository.findOne(placeId);
    }
}
