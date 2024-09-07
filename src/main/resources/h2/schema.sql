DROP TABLE IF EXISTS RESTAURANT;

-- 식당
CREATE TABLE RESTAURANT
(
    index INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,         -- 가게명, 음식명
    category VARCHAR(255) NOT NULL,      -- 카테고리
    address VARCHAR(255) NOT NULL,       -- 주소
    roadAddress VARCHAR(255),   -- 도로명 주소
    homePageLink VARCHAR(255),  -- 홈페이지 주소
    imageLink VARCHAR(255),     -- 음식, 가게 이미지 주소
    isVisit BOOLEAN NOT NULL,           -- 방문 여부
    visitCount INTEGER NOT NULL,         -- 방문 횟수
    lastVisitDate TIMESTAMP,    -- 마지막 방문 날짜
    starRating DOUBLE          -- 별점
);