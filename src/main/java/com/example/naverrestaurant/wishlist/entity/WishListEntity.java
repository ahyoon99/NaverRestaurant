package com.example.naverrestaurant.wishlist.entity;

import com.example.naverrestaurant.db.MemoryDbEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class WishListEntity extends MemoryDbEntity {
    private String title;   //가게명, 음식명
    private String category;    // 카테고리
    private String address; // 주소
    private String roadAddress; // 도로명 주소
    private String homePageLink;    // 홈페이지 주소
    private String imageLink;   // 음식, 가게 이미지 주소
    private boolean isVisit;    // 방문 여부
    private int visitCount; // 방문 횟수
    private LocalDateTime lastVisitDate;    // 마지막 방문 날짜
    private int starRating;         // 별점
}
