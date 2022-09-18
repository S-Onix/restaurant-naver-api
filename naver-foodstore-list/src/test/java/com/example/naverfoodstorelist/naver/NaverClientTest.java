package com.example.naverfoodstorelist.naver;

import com.example.naverfoodstorelist.naver.dto.SearchImageReq;
import com.example.naverfoodstorelist.naver.dto.SearchLocalReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    public void localSearchTest(){
        // 테스트 내용 : naver api 서버와 응답을 잘 주고 받는가?

        var search = new SearchLocalReq();
        search.setQuery("갈비집");

        var result = naverClient.searchLocal(search);
        System.out.println(result);


    }

    @Test
    public void imageSearchTest(){
        var search = new SearchImageReq();
        search.setQuery("갈비집");

        var result = naverClient.searchImage(search);
        System.out.println(result);
    }
}
