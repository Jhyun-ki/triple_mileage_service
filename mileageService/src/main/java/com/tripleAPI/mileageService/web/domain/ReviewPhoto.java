package com.tripleAPI.mileageService.web.domain;

import com.tripleAPI.mileageService.web.domain.enums.PhotoStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class ReviewPhoto extends BaseEntity{
    @Id
    @Column(name="attached_photo_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;

    private String attachedPhotoName;

    @Enumerated(EnumType.STRING)
    @Column(name="photo_status")
    private PhotoStatus photoStatus;
    /**
     * 연관관계 메서드
     */
    private void setReview(Review review) {
        this.review = review;
        review.getReviewPhotos().add(this);
    }

    public void setPhotoStatus(PhotoStatus photoStatus) {
        this.photoStatus = photoStatus;
    }

    private void setReviewPhotoInfo(UUID attachedPhotoId, String attachedPhotoName, PhotoStatus photoStatus) {
        this.id = attachedPhotoId;
        this.attachedPhotoName = attachedPhotoName;
        this.photoStatus = photoStatus;
    }

    public static ReviewPhoto createReviewPhoto(UUID attachedPhotoId, Review review, String attachedPhotoName, PhotoStatus photoStatus) {
        ReviewPhoto reviewPhoto = new ReviewPhoto();
        reviewPhoto.setReview(review);
        reviewPhoto.setReviewPhotoInfo(attachedPhotoId, attachedPhotoName, photoStatus);

        return reviewPhoto;
    }
}
