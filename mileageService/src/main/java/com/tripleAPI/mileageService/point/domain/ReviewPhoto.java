package com.tripleAPI.mileageService.point.domain;

import com.tripleAPI.mileageService.point.common.CommonUtil;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class ReviewPhoto extends BaseEntity{
    @Id
    @Column(name="attached_photo_id", columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;

    private String attachedPhotoName;
}
