package com.tripleAPI.mileageService.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tripleAPI.mileageService.common.CommonUtil;
import com.tripleAPI.mileageService.web.domain.enums.Action;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name="review", indexes = {@Index(name = "idx_review_place_id", columnList = "place_id")
                                ,@Index(name = "idx_review_user_id", columnList = "user_id")})
public class Review extends BaseEntity{
    @Id
    @Column(name="review_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_action")
    private Action action; // ADD, MOD, DELETE

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "review")
    private List<Point> points = new ArrayList<>();

    private int photoCount;
    /**
     * 연관관계 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void setAddReviewInfo(UUID id,Place place, Action action, String content, int photoCount) {
        this.id = id;
        this.place = place;
        this.action = action;
        this.content = content;
        this.photoCount = photoCount;
    }

    public void setModReviewInfo(String content, int photoCount) {
        this.action = Action.MOD;
        this.content = content;
        this.photoCount = photoCount;;
    }

    public void setDeleteReviewInfo() {
        this.action = Action.DELETE;
        this.photoCount = 0;
    }

    public static Review createReview(UUID id, Member member, Place place, Action action, String content, int photoCount) {
        Review review = new Review();
        review.setMember(member);
        review.setAddReviewInfo(id, place, action, content, photoCount);

        return review;
    }

}
