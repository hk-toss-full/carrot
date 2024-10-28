package com.carrot.daily.controller;

import com.carrot.daily.domain.Daily;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DailyControllerTest {

    @Test
    void writePost() {
        DailyPostRequest request = new DailyPostRequest(1L, 1L, 1L, "title", "content");

        Daily post = request.toEntity();
        assertEquals(1L, post.getUserId());
        assertEquals(1L, post.getCategoryId());
        assertEquals(1L, post.getLocationId());
        assertEquals("title", post.getTitle());
        assertEquals("content", post.getContent());
    }

//    @Test
//    ResponseEntity<List<DailyResponse>> getAllPosts() {
//
//    }


}