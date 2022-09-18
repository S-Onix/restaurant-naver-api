package com.example.naverfoodstorelist.wishlist.repository;

import com.example.naverfoodstorelist.wishlist.entity.WishListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private WishListEntity create(){
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setRoadAddress("roadAddress");
        wishList.setVisit(false);
        wishList.setVisitCnt(0);
        wishList.setImageLink("image Link");
        wishList.setHomePageLink("home page link");
        wishList.setLastVisitDate(null);

        return wishList;
    }

    @Test
    public void saveTest(){
        var wishList = create();
        var expected = wishListRepository.save(wishList);

        Assertions.assertNotNull(wishList);
        Assertions.assertEquals(1, expected.getIndex());
    }

    @Test
    public void updateTest(){
        var wishList = create();
        var expected = wishListRepository.save(wishList);

        expected.setTitle("update entity");
        var saveEntity = wishListRepository.save(expected);

        Assertions.assertEquals("update entity", saveEntity.getTitle());
        Assertions.assertEquals(1, wishListRepository.listAll().size());
    }

    @Test
    public void findByIdTest(){
        var wishList = create();
        wishListRepository.save(wishList);

        var expected = wishListRepository.findById(1);

        Assertions.assertEquals(true, expected.isPresent());
        Assertions.assertEquals(1, expected.get().getIndex());
    }

    @Test
    public void deleteByIdTest(){
        var wishList = create();
        wishListRepository.save(wishList);
        wishListRepository.deleteById(1);

        int count = wishListRepository.listAll().size();
        Assertions.assertEquals(0, count);

    }

    @Test
    public void listAllTest(){
        var wishList = create();
        wishListRepository.save(wishList);

        var wishList2 = create();
        wishListRepository.save(wishList2);

        int count = wishListRepository.listAll().size();
        Assertions.assertEquals(2, count);

    }
}
