package com.tripleAPI.mileageService.api.service;

import com.tripleAPI.mileageService.api.dto.EventsRequest;
import com.tripleAPI.mileageService.api.dto.EventsResponse;
import com.tripleAPI.mileageService.api.dto.PointListByUserId;
import com.tripleAPI.mileageService.api.dto.PointListByUserIdResponse;
import com.tripleAPI.mileageService.exception.CommonException;
import com.tripleAPI.mileageService.exception.UserNotExistException;
import com.tripleAPI.mileageService.domain.*;
import com.tripleAPI.mileageService.domain.enums.Action;
import com.tripleAPI.mileageService.domain.enums.PhotoStatus;
import com.tripleAPI.mileageService.domain.enums.PointState;
import com.tripleAPI.mileageService.domain.enums.PointType;
import com.tripleAPI.mileageService.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventsService {
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public EventsResponse addReview(EventsRequest eventsRequest) {

        //엔티티 조회
        Member member = memberRepository.findOne(eventsRequest.getUserId());
        Place place = placeRepository.findOne(eventsRequest.getPlaceId());

        Review review;
        //리뷰 생성
        if(reviewRepository.isAddReviewAvailable(member, place)) {
             review = Review.createReview(eventsRequest.getReviewId(), member, place
                                         ,eventsRequest.getAction(), eventsRequest.getContent()
                                         ,eventsRequest.getAttachedPhotoIds().size());
            reviewRepository.save(review);
        }
        else {
            throw new CommonException("리뷰는 1개의 장소에 1명의 유저만 작성 가능합니다.");
        }

        //리뷰에 첨부될 사진 생성
        for(int i=0; i < eventsRequest.getAttachedPhotoIds().size(); i++) {
            ReviewPhoto reviewPhoto = ReviewPhoto.createReviewPhoto(eventsRequest.getAttachedPhotoIds().get(i), review, String.valueOf(Math.random()), PhotoStatus.NORMAL);
            reviewPhotoRepository.save(reviewPhoto);
        }

        //포인트 지급
        //내용이 1자 이상
        if(eventsRequest.getContent().length() > 0) {
            Point point = Point.createPoint(member, review, PointState.NORMAL, PointType.CONTENT_REVIEW);
            member.addPointBalance();
            pointRepository.save(point);
        }
        //사진이 1개 이상
        if(eventsRequest.getAttachedPhotoIds().size() > 0) {
            Point point = Point.createPoint(member, review, PointState.NORMAL, PointType.PHOTO_REVIEW);
            member.addPointBalance();
            pointRepository.save(point);
        }
        //해당 장소에 대한 첫 리뷰 보너스
        if(reviewRepository.isFirstReview(place, Action.DELETE)) {
            Point point = Point.createPoint(member, review, PointState.NORMAL, PointType.FIRST_REVIEW);
            member.addPointBalance();
            pointRepository.save(point);
        }

        return new EventsResponse(review.getId(), member.getId(), member.getPointBalance());
    }

    @Transactional
    public EventsResponse modReview(EventsRequest eventsRequest) {
        //엔티티 조회
        Member member = memberRepository.findOne(eventsRequest.getUserId());
        Review review = reviewRepository.findOne(eventsRequest.getReviewId());

        if(review == null) {
            throw new CommonException("리뷰가 존재하지 않습니다.");
        }

        if(review.getAction().equals(Action.DELETE)) {
            throw new CommonException("이미 삭제된 리뷰는 수정이 불가 합니다.");
        }

        int prevPhotoCount = review.getPhotoCount();
        String prevContent = review.getContent();

        //리뷰 수정(변경감지 이용)
        review.setModReviewInfo(eventsRequest.getContent(), eventsRequest.getAttachedPhotoIds().size());

        //리뷰에 첨부될 사진 생성 또는 전부 삭제
        if(eventsRequest.getAttachedPhotoIds().size() > 0) {
            for(int i=0; i < eventsRequest.getAttachedPhotoIds().size(); i++) {
                ReviewPhoto reviewPhoto = ReviewPhoto.createReviewPhoto(eventsRequest.getAttachedPhotoIds().get(i), review, String.valueOf(Math.random()), PhotoStatus.NORMAL);
                reviewPhotoRepository.save(reviewPhoto);
            }
        }
        else {
            List<ReviewPhoto> findReviewPhotos =  reviewPhotoRepository.findByReview(review);
            for(ReviewPhoto reviewPhoto : findReviewPhotos) {
                reviewPhoto.setPhotoStatus(PhotoStatus.DELETE);
            }
        }

        //사진이 없던 리뷰에 사진을 추가했을 경우
        if(prevPhotoCount == 0 && review.getPhotoCount() > 0) {
            Point point = Point.createPoint(member, review, PointState.NORMAL, PointType.PHOTO_REVIEW);
            member.addPointBalance();
            pointRepository.save(point);
        }
        //사진이 있던 리뷰에 사진을 지웠을 경우
        else if(prevPhotoCount > 0 && review.getPhotoCount() == 0) {
            Point findPoint = pointRepository.findByReviewIdAndPointType(review, PointType.PHOTO_REVIEW, PointState.NORMAL);

            findPoint.cancelPoint();
            member.cancelPointBalance();
        }

        //내용이 없던 리뷰에 내용을 추가했을 경우
        if(prevContent.length() == 0 && review.getContent().length() > 0) {
            Point point = Point.createPoint(member, review, PointState.NORMAL, PointType.CONTENT_REVIEW);
            member.addPointBalance();
            pointRepository.save(point);
        }
        //내용이 있던 리뷰에 내용을 제거했을 경우
        else if(prevContent.length() > 0 && review.getContent().length() == 0) {
            Point findPoint = pointRepository.findByReviewIdAndPointType(review, PointType.CONTENT_REVIEW, PointState.NORMAL);

            findPoint.cancelPoint();
            member.cancelPointBalance();
        }

        return new EventsResponse(review.getId(), member.getId(), member.getPointBalance());
    }

    @Transactional
    public EventsResponse deleteReview(EventsRequest eventsRequest) {
        //엔티티 조회
        Member member = memberRepository.findOne(eventsRequest.getUserId());
        Review review = reviewRepository.findOne(eventsRequest.getReviewId());

        if(review == null) {
            throw new CommonException("리뷰가 존재하지 않습니다.");
        }

        if(review.getAction().equals(Action.DELETE)) {
            throw new CommonException("이미 삭제된 리뷰입니다.");
        }

        //리뷰 삭제
        review.setDeleteReviewInfo();

        //리뷰로 얻은 포인트 모두 회수
        List<Point> findPoints = pointRepository.findByReviewId(review, PointState.NORMAL);
        for(Point point : findPoints) {
            point.cancelPoint();
            member.cancelPointBalance();
        }

        //첨부 사진 모두 삭제 처리
        List<ReviewPhoto> findReviewPhotos =  reviewPhotoRepository.findByReview(review);
        for(ReviewPhoto reviewPhoto : findReviewPhotos) {
            reviewPhoto.setPhotoStatus(PhotoStatus.DELETE);
        }

        return new EventsResponse(review.getId(), member.getId(), member.getPointBalance());
    }

    public PointListByUserIdResponse selectPointListByUserId(UUID userId) {
        PointListByUserIdResponse pointListByUserIdResponse = new PointListByUserIdResponse();

        Member findMember = memberRepository.findOne(userId);

        if(findMember == null) {
            throw new UserNotExistException("존재하지 않는 회원 입니다.");
        }

        pointListByUserIdResponse.setUserId(findMember.getId());
        pointListByUserIdResponse.setUserName(findMember.getUserName());
        pointListByUserIdResponse.setCreated_at(findMember.getCreated_at());
        pointListByUserIdResponse.setUpdated_at(findMember.getUpdated_at());
        pointListByUserIdResponse.setPointBalance(findMember.getPointBalance());

        List<Point> findPoints = pointRepository.findByUserId(findMember);

        for(Point point : findPoints) {
            PointListByUserId pointListByUserId = new PointListByUserId();
            pointListByUserId.setPointId(point.getId());
            pointListByUserId.setPointAmt(point.getPointAmt());
            pointListByUserId.setReviewId(point.getReview().getId());
            pointListByUserId.setPointState(point.getPointState());
            pointListByUserId.setPointType(point.getPointType());
            pointListByUserId.setCreated_at(point.getCreated_at());
            pointListByUserId.setUpdated_at(point.getUpdated_at());

            pointListByUserIdResponse.getPoint().add(pointListByUserId);
        }

        return pointListByUserIdResponse;
    }
}
