package com.tripleAPI.mileageService.point.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class PointCancelDetail {
    @Id
    @Column(name="point_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private int pointUseAmt;

    @OneToOne
    @MapsId
    @JoinColumn(name="point_id")
    private Point point;
}
