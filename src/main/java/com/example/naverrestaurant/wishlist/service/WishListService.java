package com.example.naverrestaurant.wishlist.service;

import com.example.naverrestaurant.naver.NaverClient;
import com.example.naverrestaurant.naver.dto.SearchImageReq;
import com.example.naverrestaurant.naver.dto.SearchLocalReq;
import com.example.naverrestaurant.wishlist.dto.WishListDto;
import com.example.naverrestaurant.wishlist.entity.WishListEntity;
import com.example.naverrestaurant.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){
        // 지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);
        if(searchLocalRes.getTotal()>0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>",""); // 이상한 문자 자동으로 삭제

            // 이미지 검색
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);
            var searchImageRes = naverClient.searchImage(searchImageReq);
            if(searchImageRes.getTotal()>0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                // 결과를 리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());
                return result;
            }
        }
        return new WishListDto();
    }

    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);
        var saveEntity = wishListRepository.save(entity);
        return entityToDto(saveEntity);
    }

    private WishListEntity dtoToEntity(WishListDto wishListDto) {
        var entity = new WishListEntity();
        entity.setIndex(wishListDto.getIndex());
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setAddress(wishListDto.getAddress());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setHomePageLink(wishListDto.getHomePageLink());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        entity.setStarRating(wishListDto.getStarRating());
        return entity;
    }

    private WishListDto entityToDto(WishListEntity wishListEntity) {
        var dto = new WishListDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setCategory(wishListEntity.getCategory());
        dto.setAddress(wishListEntity.getAddress());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setHomePageLink(wishListEntity.getHomePageLink());
        dto.setImageLink(wishListEntity.getImageLink());
        dto.setVisit(wishListEntity.isVisit());
        dto.setVisitCount(wishListEntity.getVisitCount());
        dto.setLastVisitDate(wishListEntity.getLastVisitDate());
        dto.setStarRating(wishListEntity.getStarRating());
        return dto;
    }

    public List<WishListDto> findAll() {
        return wishListRepository.listAll()
                .stream()
                .map(it -> entityToDto(it))
                .collect(Collectors.toList());
    }

    public void delete(int index) {
        wishListRepository.deleteById(index);
    }

    public WishListDto find(int index) {
        Optional<WishListEntity> wishListEntity = wishListRepository.findById(index);
        if(!wishListEntity.isPresent()){    // wishListEntity에 값이 없으면 Exception 처리하기
            throw new IllegalArgumentException();
        }
        return entityToDto(wishListEntity.get());   // wishListEntity에 값이 있으면 wishListDto로 변환하여 리턴하기
    }

    public void addVisit(int index, int starRating) {
        var restaurant = wishListRepository.findById(index);
        if (restaurant.isPresent()){
            var restaurantEntity = restaurant.get();
            if(!restaurantEntity.isVisit()){     // 방문한 적 없는 식당인 경우, isVisit을 true로 변경해주기
                restaurantEntity.setVisit(true);
            }

            // 평균 별점 계산해주기
            int restaurantEntityVisitCount = restaurantEntity.getVisitCount();
            double restaurantEntityStarRating = restaurantEntity.getStarRating();

            restaurantEntityStarRating = (Math.round(((restaurantEntityStarRating*restaurantEntityVisitCount+starRating)/(restaurantEntityVisitCount+1))*100)/100.0);

            restaurantEntity.setVisitCount(restaurantEntityVisitCount+1);
            restaurantEntity.setStarRating(restaurantEntityStarRating);
            restaurantEntity.setLastVisitDate(LocalDateTime.now());
            wishListRepository.updateById(index, restaurantEntity);

            var result = wishListRepository.findById(index);
            System.out.println(result.toString());
        }
        else{   // index에 해당하는 WishListEntity가 존재하지 않을 때

        }
    }
}
