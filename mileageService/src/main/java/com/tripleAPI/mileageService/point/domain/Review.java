package com.tripleAPI.mileageService.point.domain;

import com.tripleAPI.mileageService.point.common.CommonUtil;
import com.tripleAPI.mileageService.point.domain.enums.Action;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class Review extends BaseEntity{
    @Id
    @Column(name="review_id", columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

    @Enumerated(EnumType.STRING)
    private Action action; // ADD, MOD, DELETE

    @Column(columnDefinition = "TEXT")
    private String content;

}
