package com.sparta.myselectshopbeta.naver.service;

import com.sparta.myselectshopbeta.naver.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverApiService {
     // api 설명서
     // https://developers.naver.com/docs/serviceapi/search/shopping/shopping.md#%EC%98%A4%EB%A5%98-%EC%BD%94%EB%93%9C
     public List<ItemDto> searchItems(String query) {
          RestTemplate rest = new RestTemplate();
          HttpHeaders headers = new HttpHeaders();
          headers.add("X-Naver-Client-Id", "BsF30Ygo5lAucAXAw8Os");
          headers.add("X-Naver-Client-Secret", "lIZFohjQfp");
          String body = "";
          
          HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
          ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?display=15&query=" + query , HttpMethod.GET, requestEntity, String.class);
          
          HttpStatus httpStatus = responseEntity.getStatusCode();
          int status = httpStatus.value();
          log.info("NAVER API Status Code : " + status);
          
          String response = responseEntity.getBody();
          
          return fromJSONtoItems(response);
     }
     
     public List<ItemDto> fromJSONtoItems(String response) {
          
          JSONObject rjson = new JSONObject(response);
          JSONArray items  = rjson.getJSONArray("items");
          List<ItemDto> itemDtoList = new ArrayList<>();
          
          for (int i=0; i<items.length(); i++) {
               JSONObject itemJson = items.getJSONObject(i);
               ItemDto itemDto = new ItemDto(itemJson);
               itemDtoList.add(itemDto);
          }
          
          return itemDtoList;
     }
}