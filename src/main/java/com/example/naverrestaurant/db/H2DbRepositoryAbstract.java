package com.example.naverrestaurant.db;

import com.example.naverrestaurant.wishlist.entity.WishListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;



abstract public class H2DbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<T> findById(int index) {
        String findByIdQuery = "select * from restaurant where index=? limit 1";
        int findByIdParam = index;

        WishListEntity wishListEntity = jdbcTemplate.queryForObject(findByIdQuery,
                wishListEntityRowMapper(),
                index);

        return (Optional<T>) Optional.of(wishListEntity);
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public void deleteById(int index) {

    }

    @Override
    public List<T> listAll() {
        return null;
    }

    private RowMapper<WishListEntity> wishListEntityRowMapper(){
        return ((rs, rowNum) -> {
            WishListEntity wishListEntity = new WishListEntity();
            wishListEntity.setIndex(rs.getInt("index"));
            wishListEntity.setTitle(rs.getString("title"));
            wishListEntity.setCategory(rs.getString("category"));
            wishListEntity.setAddress(rs.getString("address"));
            wishListEntity.setRoadAddress(rs.getString("roadAddress"));
            wishListEntity.setHomePageLink(rs.getString("homePageLink"));
            wishListEntity.setImageLink(rs.getString("imageLink"));
            wishListEntity.setVisit(rs.getBoolean("isVisit"));
            wishListEntity.setVisitCount(rs.getInt("visitCount"));
            wishListEntity.setLastVisitDate(rs.getTimestamp("lastVisitDate").toLocalDateTime());

            return wishListEntity;
        });
    }

}
