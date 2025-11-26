package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.req.ReviewCreateReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewCreateResDTO;
import com.example.umc9th.domain.review.dto.res.ReviewListDTO;
import com.example.umc9th.domain.review.entitiy.Review;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ApiResponse<ReviewCreateResDTO> createReview(@RequestBody ReviewCreateReqDTO request){

        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                reviewService.createReview(request));
    }

    // 내 리뷰 조회
    @GetMapping("/my")
    public ApiResponse<List<Review>> searchReviews(
            @RequestParam(required = false) String locationName,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer starRange,
            @RequestParam Long memberId
    ){
        List<Review> reviews = reviewService.searchReviews(locationName, storeName, starRange, memberId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviews);
    }

    // 내가 작성한 미션 목록 조회
    @GetMapping("/myReviewList")
    public ApiResponse<ReviewListDTO> getMyReviews(
            @ValidPage @RequestParam Integer page
    ){
        Long loginMemberId = 1L;

        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                reviewService.getMyReviews(loginMemberId, page)
        );
    }
}
