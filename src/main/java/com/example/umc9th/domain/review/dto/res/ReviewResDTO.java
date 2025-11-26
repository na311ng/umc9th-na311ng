package com.example.umc9th.domain.review.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResDTO {
    private Long reviewId;
    private String storeName;
    private Float star;
    private String content;
    private String createdAt;
}
