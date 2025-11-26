package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.req.MissionCreateReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionCreateResDTO;
import com.example.umc9th.domain.mission.dto.res.MissionHomeResponse;
import com.example.umc9th.domain.mission.dto.res.StoreMissionListDTO;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {
    private final MissionService missionService;

    // 지역별 미션 조회
    @GetMapping
    public ApiResponse<Page<MissionHomeResponse>>  getMissionsByLocation(
            @RequestParam Long locationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MissionHomeResponse> missions = missionService.getMissionsByLocation(locationId, PageRequest.of(page, size));
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missions);
    }

    // 가게에 미션 추가하기
    @PostMapping("/{storeId}/missions")
    public ApiResponse<MissionCreateResDTO> createMission(
            @PathVariable Long storeId,
            @RequestBody MissionCreateReqDTO dto
    ){
        return ApiResponse.onSuccess(
                GeneralSuccessCode.CREATED,
                missionService.createMission(storeId, dto)
        );
    }

    // 가게별 미션 목록 조회
    @GetMapping("/stores/{storeId}")
    public ApiResponse<StoreMissionListDTO> getStoreMissions(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page
    ){
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                missionService.getMissionByStore(storeId, page)
        );
    }
}
