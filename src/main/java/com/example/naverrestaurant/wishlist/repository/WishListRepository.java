package com.example.naverrestaurant.wishlist.repository;

import com.example.naverrestaurant.db.MemoryDbRepositoryAbstract;
import com.example.naverrestaurant.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {
}
