package com.example.naverrestaurant.wishlist.service;

import com.example.naverrestaurant.wishlist.dto.WishListDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    public void findAllTest(){
        for(int i=1;i<=3;i++){
            var wishListDto = new WishListDto();
            wishListDto.setTitle("마라탕"+i);
            wishListDto.setCategory("중식");
            var saveEntity = wishListService.add(wishListDto);
        }
        List<WishListDto> wishList = wishListService.findAll();

        Assertions.assertEquals(wishList.size(), 3);
    }

    @Test
    public void deleteTest(){
        for(int i=1;i<=3;i++){
            var wishListDto = new WishListDto();
            wishListDto.setTitle("마라탕"+i);
            wishListDto.setCategory("중식");
            var saveEntity = wishListService.add(wishListDto);
        }
        wishListService.delete(1);
        List<WishListDto> wishList = wishListService.findAll();

        Assertions.assertEquals(wishList.size(), 2);
    }
}
