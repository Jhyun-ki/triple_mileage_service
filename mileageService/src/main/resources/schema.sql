CREATE TABLE member (
    user_id       BINARY(16)  NOT NULL,
    user_name     VARCHAR(50) NOT NULL,
    point_balance INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id)
);

CREATE TABLE place (
   place_id BINARY(16) NOT NULL,
   place_name VARCHAR(255),
   PRIMARY KEY(place_id)
);

CREATE TABLE point (
    point_id BINARY(16) NOT NULL,
    point_amt INTEGER DEFAULT 0 NOT NULL,
    point_state VARCHAR(6) NOT NULL,
    point_type VARCHAR(20) NOT NULL,
    user_id BINARY(16) NOT NULL,
    review_id BINARY(16) NOT NULL,
    PRIMARY KEY (point_id)
);

CREATE TABLE review (
    review_id BINARY(16) NOT NULL,
    action VARCHAR(6) NOT NULL,
    content TEXT,
    user_id BINARY(16) NOT NULL,
    place_id BINARY(16) NOT NULL,
    PRIMARY KEY (review_id)
);

create table review_photo (
    attached_photo_id BINARY(16) NOT NULL,
    attached_photo_name varchar(255),
    review_id BINARY(16) NOT NULL ,
    primary key (attached_photo_id)
);

ALTER TABLE point ADD CONSTRAINT fk_point_member_user_id FOREIGN KEY (user_id) references member (user_id);
ALTER TABLE point ADD CONSTRAINT fk_point_review_review_id FOREIGN KEY (review_id) references review (review_id);
ALTER TABLE review ADD CONSTRAINT fk_review_place_place_id FOREIGN KEY (place_id) references place (place_id);
ALTER TABLE review ADD CONSTRAINT fk_review_member_user_id FOREIGN KEY (user_id) references member (user_id);
ALTER TABLE review_photo ADD CONSTRAINT fk_review_photo_review_review_id FOREIGN KEY (review_id) references review (review_id);


