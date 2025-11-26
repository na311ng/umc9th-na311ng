package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.req.MemberMissionCreateReqDTO;
import com.example.umc9th.domain.mission.dto.res.MemberMissionCreateResDTO;
import com.example.umc9th.domain.mission.dto.res.MemberMissionResponse;
import com.example.umc9th.domain.mission.dto.res.MyMissionListDTO;
import com.example.umc9th.domain.mission.service.MemberMissionService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MemberMissionController {
    private final MemberMissionService memberMissionService;

    // 나의 미션 목록 조회
    @GetMapping("/my")
    public ApiResponse<Page<MemberMissionResponse>>  getMyMissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long loginMemberId = 1L; // TODO: 실제는 JWT 토큰에서 추출
        Page <MemberMissionResponse> missions = memberMissionService.getMyMissions(loginMemberId, page, size);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missions);
    }

    // 가게의 미션 도전하기
    @PostMapping("/{missionId}/challenge")
    public ApiResponse<MemberMissionCreateResDTO> challengeMission(
            @PathVariable Long missionId,
            @RequestBody MemberMissionCreateReqDTO request
    ){
        return ApiResponse.onSuccess(
                GeneralSuccessCode.CREATED,
                memberMissionService.challengeMission(missionId, request)
        );
    }

    // 내가 진행중인 미션 목록 API 구현
    @GetMapping("/my-progress")
    public ApiResponse<MyMissionListDTO> getMyProgressMissions(
            @ValidPage @RequestParam(required = false) Integer page
    ){
        Long loginMemberId = 1L;

        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberMissionService.getMyProgressMissions(loginMemberId, page)
        );
    }

}
