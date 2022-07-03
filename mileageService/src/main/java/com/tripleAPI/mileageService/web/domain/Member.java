package com.tripleAPI.mileageService.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tripleAPI.mileageService.common.CommonUtil;
import com.tripleAPI.mileageService.exception.CommonException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Member extends BaseEntity{
    @Id
    @Column(name="user_id", columnDefinition = "BINARY(16)")
    private UUID id = CommonUtil.createSequentialUUID();

    private String userName;
    private int pointBalance;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Point> points = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    public Member(){}

    public Member(String userName) {
        this.userName = userName;
    }

    public void addPointBalance() {
        int pointBalance = this.pointBalance + 1;
        this.pointBalance = pointBalance;
    }

    public void cancelPointBalance() {
        int pointBalance = this.pointBalance - 1;
        if(pointBalance < 0) {
            throw new CommonException("포인트는 -값이 될 수 없습니다.");
        } else {
            this.pointBalance = pointBalance;
        }
    }
}
