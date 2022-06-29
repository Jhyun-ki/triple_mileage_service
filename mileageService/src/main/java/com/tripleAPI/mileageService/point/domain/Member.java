package com.tripleAPI.mileageService.point.domain;

import com.tripleAPI.mileageService.point.common.CommonUtil;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Member extends BaseEntity{
    @Id
    @Column(name="user_id", columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();

    private String userName;
    private int pointBalance;
}
