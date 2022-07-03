package com.tripleAPI.mileageService.web.service;

import com.tripleAPI.mileageService.web.domain.Member;
import com.tripleAPI.mileageService.web.domain.Place;
import com.tripleAPI.mileageService.web.domain.Review;
import com.tripleAPI.mileageService.web.repository.ReviewRepository;
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

    /**
     * 장소 등록
     */
    @Transactional
    public UUID join(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    /**
     * 장소 전체 조회
     */
    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

    public Review findOne(UUID reviewId) {
        return reviewRepository.findOne(reviewId);
    }

    public List<Review> findReviewsByMemberAndPlace(Member member, Place place) {return reviewRepository.findByMemberAndPlace(member, place);}
}
