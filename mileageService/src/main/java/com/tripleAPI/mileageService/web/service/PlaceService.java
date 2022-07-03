package com.tripleAPI.mileageService.web.service;

import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.repository.PlaceRepository;
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

    /**
     * 장소 등록
     */
    @Transactional
    public UUID join(Place place) {
        placeRepository.save(place);
        return place.getId();
    }

    /**
     * 장소 전체 조회
     */
    public List<Place> findPlaces() {
        return placeRepository.findAll();
    }

    public Place findOne(UUID placeId) {
        return placeRepository.findOne(placeId);
    }
}
