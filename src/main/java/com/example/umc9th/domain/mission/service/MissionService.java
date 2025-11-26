package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.converter.StoreMissionConverter;
import com.example.umc9th.domain.mission.dto.req.MissionCreateReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionCreateResDTO;
import com.example.umc9th.domain.mission.dto.res.MissionHomeResponse;
import com.example.umc9th.domain.mission.dto.res.StoreMissionListDTO;
import com.example.umc9th.domain.mission.entitiy.Mission;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entitiy.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    public Page<MissionHomeResponse> getMissionsByLocation(Long locationId, Pageable pageable) {
        return missionRepository.findMissionsByLocationId(locationId, pageable);
    }

    @Transactional
    public MissionCreateResDTO createMission(Long storeId, MissionCreateReqDTO dto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new GeneralException(GeneralErrorCode.NOT_FOUND));

        Mission mission = MissionConverter.toEntity(dto, store);
        Mission saved = missionRepository.save(mission);

        return MissionConverter.toResDTO(saved);
    }

    public StoreMissionListDTO getMissionByStore(Long storeId, int page) {

        var pageable = PageRequest.of(page -1, 10);

        var missions = missionRepository.findAllByStoreId(storeId, pageable);

        return StoreMissionConverter.toListDTO(missions);
    }
}
