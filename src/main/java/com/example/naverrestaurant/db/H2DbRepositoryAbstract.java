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
        if(entity.getIndex() == null){
            String saveQuery = "insert into " +
                    "restaurant(title, category, address, roadAddress, homepageLink, imageLink, isvisit, visitCount, lastVisitDate, starRating) " +
                    "values (?,?,?,?,?,?,?,?,?,?)";

            WishListEntity wishListEntity = (WishListEntity) entity;
            Object []saveParams = new Object[] {wishListEntity.getTitle(),
                    wishListEntity.getCategory(), wishListEntity.getAddress(), wishListEntity.getRoadAddress(),
                    wishListEntity.getHomePageLink(), wishListEntity.getImageLink(), wishListEntity.isVisit(),
                    wishListEntity.getVisitCount(), wishListEntity.getLastVisitDate(), wishListEntity.getStarRating()};

            jdbcTemplate.update(saveQuery, saveParams);

            String lastInsertIndexQuery = "select max(index) from restaurant";
            int listInsertIndex = jdbcTemplate.queryForObject(lastInsertIndexQuery, int.class);

            String lastInsertEntityQuery = "select * from restaurant where index=?";
            WishListEntity result = jdbcTemplate.queryForObject(lastInsertEntityQuery, wishListEntityRowMapper(), listInsertIndex);
            return (T) result;
        }
        else{
            updateById(entity.getIndex(), entity);
            String updateEntityQuery = "select * from restaurant where index=?";
            WishListEntity result = jdbcTemplate.queryForObject(updateEntityQuery, wishListEntityRowMapper(), entity.getIndex());
            return (T) result;
        }
    }

    @Override
    public void deleteById(int index) {
        String deleteByIdQuery = "delete from restaurant where index=?";
        int deleteByIdParam = index;
        int result = this.jdbcTemplate.update(deleteByIdQuery, deleteByIdParam);
        if(result==1){
            System.out.println("delete success");
        }
        else if(result==0){
            System.out.println("delete fail");
        }
    }

    @Override
    public List<T> listAll() {
        String listAllQuery = "select * from restaurant";
        List<WishListEntity> result = this.jdbcTemplate.query(listAllQuery, wishListEntityRowMapper());
        return (List<T>) result;
    }

    @Override
    public void updateById(int index, T entity) {
        String updateByIdQuery = "update restaurant " +
                "set title=?, category=?, address=?, roadAddress=?, homepageLink=?, imageLink=?, isvisit=?, visitCount=?, lastVisitDate=?, starRating=? where index=?";

        WishListEntity wishListEntity = (WishListEntity) entity;
        Object []updateParams = new Object[] {wishListEntity.getTitle(),
                wishListEntity.getCategory(), wishListEntity.getAddress(), wishListEntity.getRoadAddress(),
                wishListEntity.getHomePageLink(), wishListEntity.getImageLink(), wishListEntity.isVisit(),
                wishListEntity.getVisitCount(), wishListEntity.getLastVisitDate(), wishListEntity.getStarRating(), index};

        jdbcTemplate.update(updateByIdQuery, updateParams);

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
            if(rs.getTimestamp("lastVisitDate") != null){   // lastVisitDate가 null이 아닐 때만 set 해주기
                wishListEntity.setLastVisitDate(rs.getTimestamp("lastVisitDate").toLocalDateTime());
            }
            wishListEntity.setStarRating((rs.getInt("starRating")));
            return wishListEntity;
        });
    }

}