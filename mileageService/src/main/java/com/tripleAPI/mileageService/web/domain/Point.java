package com.tripleAPI.mileageService.web.domain;

import com.tripleAPI.mileageService.common.CommonUtil;
import com.tripleAPI.mileageService.web.domain.enums.PointState;
import com.tripleAPI.mileageService.web.domain.enums.PointType;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name="point", indexes = {@Index(name = "idx_point_review_id", columnList = "review_id")
                               ,@Index(name = "idx_point_point_type", columnList = "point_type")})
public class Point extends BaseEntity{
    @Id
    @Column(name = "point_id", columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;

    private int pointAmt;

    @OneToOne(mappedBy = "point")
    @PrimaryKeyJoinColumn
    private PointCancelDetail pointCancelDetail;

    @Enumerated(EnumType.STRING)
    private PointState pointState; // NORMAL, CANCEL

    @Enumerated(EnumType.STRING)
    @Column(name="point_type")
    private PointType pointType; // NORMAL_REVIEW, FIRST_REVIEW

    public Point(){}

    /**
     * 연관관계 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getPoints().add(this);
    }

    public void setReview(Review review) {
        this.review = review;
        review.getPoints().add(this);
    }


    public void setAddPointInfo(PointState pointState, PointType pointType) {
        this.pointState = pointState;
        this.pointType = pointType;
        this.pointAmt = 1;
    }

    public static Point createPoint(Member member, Review review, PointState pointState, PointType pointType) {
        Point point = new Point();
        point.setMember(member);
        point.setReview(review);

        point.setAddPointInfo(pointState, pointType);
        return point;
    }

    public void cancelPoint() {
        this.pointState = PointState.CANCEL;
    }

}
