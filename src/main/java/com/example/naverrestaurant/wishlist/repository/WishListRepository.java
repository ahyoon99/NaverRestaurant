package com.example.naverrestaurant.wishlist.repository;
import com.example.naverrestaurant.db.H2DbRepositoryAbstract;
import com.example.naverrestaurant.db.MemoryDbRepositoryAbstract;
import com.example.naverrestaurant.db.MemoryDbRepositoryIfs;
import com.example.naverrestaurant.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository extends H2DbRepositoryAbstract<WishListEntity> {
}
//public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {
//}
