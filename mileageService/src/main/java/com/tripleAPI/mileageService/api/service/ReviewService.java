package com.tripleAPI.mileageService.api.service;

import com.tripleAPI.mileageService.domain.Member;
import com.tripleAPI.mileageService.domain.Place;
import com.tripleAPI.mileageService.domain.Review;
import com.tripleAPI.mileageService.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //repository를 생성자 주입 해줌
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public UUID join(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    public Review findOne(UUID reviewId) {
        return reviewRepository.findOne(reviewId);
    }

    public List<Review> findReviewsByMemberAndPlace(Member member, Place place) {return reviewRepository.findByMemberAndPlace(member, place);}
}
