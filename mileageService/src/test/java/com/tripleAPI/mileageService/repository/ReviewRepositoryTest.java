package com.tripleAPI.mileageService.repository;

import com.tripleAPI.mileageService.common.CommonUtil;
import com.tripleAPI.mileageService.domain.Member;
import com.tripleAPI.mileageService.domain.Place;
import com.tripleAPI.mileageService.domain.Review;
import com.tripleAPI.mileageService.domain.enums.Action;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired ReviewRepository reviewRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PlaceRepository placeRepository;

    @Test
    public void isFirstReview() {
        //given
        Place place = new Place("tokyo");
        Member member = new Member("hyunki");

        Review review = Review.createReview(CommonUtil.createSequentialUUID(), member, place, Action.ADD, "좋아요", 1);


        //when
        boolean isFirst = reviewRepository.isFirstReview(place, Action.DELETE);
        reviewRepository.save(review);


        //then
        assertEquals(false, isFirst);
        System.out.println("isFirst : " +  isFirst);
    }
}