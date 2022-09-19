package com.example.naverfoodstorelist.wishlist.service;

import com.example.naverfoodstorelist.naver.NaverClient;
import com.example.naverfoodstorelist.naver.dto.SearchImageReq;
import com.example.naverfoodstorelist.naver.dto.SearchLocalReq;
import com.example.naverfoodstorelist.wishlist.dto.WishListDto;
import com.example.naverfoodstorelist.wishlist.entity.WishListEntity;
import com.example.naverfoodstorelist.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){

        //지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() > 0 ) {
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);


            var searchImageRes = naverClient.searchImage(searchImageReq);

            if (searchImageRes.getTotal() > 0) {
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

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
        //이미지검색
        //결과 출력
        return new WishListDto();
    }


    public WishListDto add(WishListDto wishListDto) {
        //dto to entity > save
        var entity = dtoToEntity(wishListDto);
        var saveEntity =  wishListRepository.save(entity);
        return entityToDto(saveEntity);

    }

    private WishListEntity dtoToEntity(WishListDto dto){
        var entity = new WishListEntity();
        entity.setIndex(dto.getIndex());
        entity.setVisit(dto.isVisit());
        entity.setLastVisitDate(dto.getLastVisitDate());
        entity.setAddress(dto.getAddress());
        entity.setTitle(dto.getTitle());
        entity.setCategory(dto.getCategory());
        entity.setRoadAddress(dto.getRoadAddress());
        entity.setImageLink(dto.getImageLink());
        entity.setHomePageLink(dto.getHomePageLink());
        entity.setVisitCnt(dto.getVisitCnt());

        return entity;
    }

    private WishListDto entityToDto(WishListEntity entity){
        var dto = new WishListDto();
        dto.setIndex(entity.getIndex());
        dto.setVisit(entity.isVisit());
        dto.setLastVisitDate(entity.getLastVisitDate());
        dto.setAddress(entity.getAddress());
        dto.setTitle(entity.getTitle());
        dto.setCategory(entity.getCategory());
        dto.setRoadAddress(entity.getRoadAddress());
        dto.setImageLink(entity.getImageLink());
        dto.setHomePageLink(entity.getHomePageLink());
        dto.setVisitCnt(entity.getVisitCnt());

        return dto;
    }

    public List<WishListDto> findAll() {
        return wishListRepository.findAll()
                .stream()
                .map(it -> entityToDto(it))
                .collect(Collectors.toList());
    }

    public void remove(Integer index) {
        wishListRepository.deleteById(index);
    }

    public void addVisit(int index) {
        var wishiItem = wishListRepository.findById(index);
        if(wishiItem.isPresent()){
            var item = wishiItem.get();
            item.setVisit(true);
            item.setVisitCnt(item.getVisitCnt()+1);
        }

    }
}
