package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entitiy.Member;
import com.example.umc9th.domain.review.dto.req.ReviewCreateReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewCreateResDTO;
import com.example.umc9th.domain.review.dto.res.ReviewListDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entitiy.Review;
import com.example.umc9th.domain.store.entitiy.Store;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class ReviewConverter {

    public static Review toEntity(ReviewCreateReqDTO dto, Member member, Store store){
        return Review.builder()
                .content(dto.getContent())
                .star(dto.getStar())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewCreateResDTO toResDTO(Review review){
        return ReviewCreateResDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .memberName(review.getMember().getName())
                .content(review.getContent())
                .star(review.getStar())
                .build();
    }

    public static ReviewResDTO toReviewDTO(Review review){
        return ReviewResDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .star(review.getStar())
                .content(review.getContent())
                .createdAt(review.getCreatedAt().toString())
                .build();
    }

    public static ReviewListDTO toReviewListDTO(Page<Review> page){
        return ReviewListDTO.builder()
                .reviews(
                        page.getContent()
                                .stream()
                                .map(ReviewConverter::toReviewDTO)
                                .collect(Collectors.toList())
                )
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .build();
    }
}
