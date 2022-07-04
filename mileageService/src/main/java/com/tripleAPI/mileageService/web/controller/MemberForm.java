package com.tripleAPI.mileageService.web.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter @Setter
public class MemberForm {
    @NotNull(message = "회원을 선택해 주세요")
    private UUID userId;
}
