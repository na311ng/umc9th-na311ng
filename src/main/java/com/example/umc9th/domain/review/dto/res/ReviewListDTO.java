package com.example.umc9th.domain.review.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListDTO {
    private List<ReviewResDTO> reviews;
    private int currentPage;
    private int totalPages;
}
