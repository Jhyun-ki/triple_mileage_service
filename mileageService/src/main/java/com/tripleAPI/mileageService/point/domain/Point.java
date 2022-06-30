package com.tripleAPI.mileageService.point.domain;

import com.tripleAPI.mileageService.point.common.CommonUtil;
import com.tripleAPI.mileageService.point.domain.enums.PointState;
import com.tripleAPI.mileageService.point.domain.enums.PointType;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
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
    private PointType pointType; // NORMAL_REVIEW, FIRST_REVIEW
}
