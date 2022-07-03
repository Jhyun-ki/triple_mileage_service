package com.tripleAPI.mileageService.web.domain;

import com.tripleAPI.mileageService.common.CommonUtil;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Place extends BaseEntity{
    @Id
    @Column(name="place_id",columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();;
    private String placeName;

    public Place(){}
    public Place(String placeName) {
        this.placeName = placeName;
    }
}
