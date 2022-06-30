CREATE TABLE member COMMENT '회원' (
    user_id       BINARY(16)  NOT NULL               COMMENT '회원 아이디',
    user_name     VARCHAR(50) NOT NULL               COMMENT '회원 이름',
    point_balance INTEGER     NOT NULL DEFAULT 0     COMMENT '포인트 잔액',
    created_at    DATETIME(6) NOT NULL DEFAULT NOW() COMMENT '등록 일시',
    updated_at    DATETIME(6)                        COMMENT '수정 일시',
    PRIMARY KEY (user_id)
);

CREATE TABLE place COMMENT '리뷰 장소'(
   place_id   BINARY(16)   NOT NULL               COMMENT '리뷰 장소 아이디',
   place_name VARCHAR(255)                        COMMENT '리뷰 장소 이름',
   created_at DATETIME(6)  NOT NULL DEFAULT NOW() COMMENT '등록 일시',
   updated_at DATETIME(6)                         COMMENT '수정 일시',
   PRIMARY KEY(place_id)
);

CREATE TABLE point COMMENT '포인트 관리'(
    point_id    BINARY(16)  NOT NULL               COMMENT '포인트 아이디',
    point_amt   INTEGER     NOT NULL               COMMENT '포인트 금액',
    point_state VARCHAR(6)  NOT NULL               COMMENT '포인트 상태(NORMAL:정상, CANCEL:취소)',
    point_type  VARCHAR(20) NOT NULL               COMMENT '포인트 타입(NORMAL_REVIEW:보통 리뷰에 의한 포인트 지급, FIRST_REVIEW:첫 리뷰에 의한 포인트 지급)',
    user_id     BINARY(16)  NOT NULL               COMMENT '회원 아이디',
    review_id   BINARY(16)  NOT NULL               COMMENT '리뷰 아이디',
    created_at  DATETIME(6) NOT NULL DEFAULT NOW() COMMENT '등록 일시',
    updated_at  DATETIME(6)                        COMMENT '수정 일시',
    PRIMARY KEY (point_id)
);

CREATE TABLE point_cancel_detail COMMENT '포인트 취소 이력 관리'(
    point_id        BINARY(16)  NOT NULL COMMENT '포인트 아이디',
    point_use_amt   INTEGER     NOT NULL COMMENT '포인트 사용 금액',
    PRIMARY KEY (point_id)
);

CREATE TABLE review COMMENT '리뷰'(
    review_id     BINARY(16) NOT NULL                COMMENT '리뷰 아이디',
    review_status VARCHAR(6) NOT NULL                COMMENT '리뷰의 상태(ADD:추가, MOD:수정, DELETE:삭제)',
    content       TEXT                               COMMENT '리뷰 내용',
    user_id       BINARY(16) NOT NULL                COMMENT '회원 아이디',
    place_id      BINARY(16) NOT NULL                COMMENT '장소 아이디',
    created_at    DATETIME(6) NOT NULL DEFAULT NOW() COMMENT '등록 일시',
    updated_at    DATETIME(6)                        COMMENT '수정 일시',
    PRIMARY KEY (review_id)
);

CREATE TABLE review_photo COMMENT '리뷰 사진' (
    attached_photo_id BINARY(16) NOT NULL          COMMENT '리뷰에 첨부된 사진 아이디',
    attached_photo_name varchar(255)               COMMENT '리뷰에 첨부된 사진 이름' ,
    review_id BINARY(16) NOT NULL                  COMMENT '리뷰 아이디',
    created_at  DATETIME(6) NOT NULL DEFAULT NOW() COMMENT '등록 일시',
    updated_at  DATETIME(6)                        COMMENT '수정 일시',
    primary key (attached_photo_id)
);

ALTER TABLE point ADD CONSTRAINT fk_point_member_user_id FOREIGN KEY (user_id) references member (user_id);
ALTER TABLE point ADD CONSTRAINT fk_point_review_review_id FOREIGN KEY (review_id) references review (review_id);
ALTER TABLE point_cancel_detail ADD CONSTRAINT fk_point_cancel_detail_point_point_id FOREIGN KEY (point_id) references point (point_id);
ALTER TABLE review ADD CONSTRAINT fk_review_place_place_id FOREIGN KEY (place_id) references place (place_id);
ALTER TABLE review ADD CONSTRAINT fk_review_member_user_id FOREIGN KEY (user_id) references member (user_id);
ALTER TABLE review_photo ADD CONSTRAINT fk_review_photo_review_review_id FOREIGN KEY (review_id) references review (review_id);


