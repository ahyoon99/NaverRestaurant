package com.example.naverrestaurant.naver;

import com.example.naverrestaurant.naver.dto.SearchImageReq;
import com.example.naverrestaurant.naver.dto.SearchImageRes;
import com.example.naverrestaurant.naver.dto.SearchLocalReq;
import com.example.naverrestaurant.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;

@Component
public class NaverClient {
    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    // 장소를 검색하는 메소드
    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq){
        // << 요청 보내는 방법 >>
        // 1. 주소(uri) 만들기
        var uri = UriComponentsBuilder.fromUriString(naverLocalSearchUrl)
                .queryParams(searchLocalReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        // 2. 헤더 세팅하기
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id",naverClientId);
        headers.set("X-Naver-Client-Secret",naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. 요청해주기
        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }

    // 이미지를 검색하는 메소드
    public SearchImageRes searchImage(SearchImageReq searchImageReq){
        // << 요청 보내는 방법 >>
        // 1. 주소(uri) 만들기
        var uri = UriComponentsBuilder.fromUriString(naverImageSearchUrl)
                .queryParams(searchImageReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        // 2. 헤더 세팅하기
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id",naverClientId);
        headers.set("X-Naver-Client-Secret",naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. 요청해주기
        var httpEntity = new HttpEntity<>(headers); // 헤더를 entity에 담아주기(requestType을 정의해주기)
        var responseType = new ParameterizedTypeReference<SearchImageRes>(){};  // responseType을 정의해주기

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody();
    }
}
