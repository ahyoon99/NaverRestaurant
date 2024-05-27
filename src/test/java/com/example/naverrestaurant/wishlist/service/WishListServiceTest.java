package com.example.naverrestaurant.wishlist.service;

import com.example.naverrestaurant.wishlist.dto.WishListDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private WishListService wishListService;

    @Test
    public void searchTest(){
        var result = wishListService.search("갈비집");
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void addTest(){
        var wishListDto = new WishListDto();
        wishListDto.setTitle("전주마라탕");
        wishListDto.setCategory("중식");

        var saveEntity = wishListService.add(wishListDto);

        Assertions.assertNotNull(saveEntity);
        Assertions.assertEquals(saveEntity.getIndex(),1);
        Assertions.assertEquals(saveEntity.getTitle(), "전주마라탕");
        Assertions.assertEquals(saveEntity.getCategory(), "중식");
    }
}
