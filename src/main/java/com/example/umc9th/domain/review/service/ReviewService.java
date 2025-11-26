package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.member.entitiy.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.req.ReviewCreateReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewCreateResDTO;
import com.example.umc9th.domain.review.dto.res.ReviewListDTO;
import com.example.umc9th.domain.review.entitiy.QReview;
import com.example.umc9th.domain.review.entitiy.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entitiy.QLocation;
import com.example.umc9th.domain.store.entitiy.QStore;
import com.example.umc9th.domain.store.entitiy.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewCreateResDTO createReview(ReviewCreateReqDTO request){
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(()->new GeneralException(GeneralErrorCode.NOT_FOUND));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(()-> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Review review = ReviewConverter.toEntity(request,member,store);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toResDTO(saved);

    }
    // 내가 작성한 리뷰 + 필터링
    public List<Review> searchReviews(
            String locationName,
            String storeName,
            Integer starRange,
            Long memberId
    ){
        QReview review = QReview.review;
        QStore store = QStore.store;
        QLocation location = QLocation.location;

        BooleanBuilder builder = new BooleanBuilder();

        //
        if(locationName != null && locationName.isEmpty()){
            builder.and(location.name.containsIgnoreCase(locationName));
        }

        // 가게명 필터
        if (storeName != null && !storeName.isEmpty()) {
            builder.and(store.name.containsIgnoreCase(storeName));
        }

        // 별점 필터
        if (starRange != null) {
            switch (starRange) {
                case 5 -> builder.and(review.star.between(4.5, 5.0));
                case 4 -> builder.and(review.star.between(3.5, 4.49));
                case 3 -> builder.and(review.star.between(2.5, 3.49));
                default -> builder.and(review.star.lt(2.5));
            }
        }

        // 사용자 필터 (내 리뷰만)
        if (memberId != null) {
            builder.and(review.member.id.eq(memberId));
        }

        return reviewRepository.searchReviews(builder);
    }

    public ReviewListDTO getMyReviews(Long memberId, int page) {

        var pageable = PageRequest.of(page - 1, 10);

        var reviewPage = reviewRepository.findAllByMemberId(memberId, pageable);

        return ReviewConverter.toReviewListDTO(reviewPage);
    }
}
