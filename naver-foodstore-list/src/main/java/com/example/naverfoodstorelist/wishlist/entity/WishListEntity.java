package com.example.naverfoodstorelist.wishlist.entity;

import com.example.naverfoodstorelist.db.MemoryDBEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishListEntity extends MemoryDBEntity {
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String homePageLink;
    private String imageLink;
    private boolean isVisit;
    private int visitCnt;
    private LocalDateTime lastVisitDate;
}
