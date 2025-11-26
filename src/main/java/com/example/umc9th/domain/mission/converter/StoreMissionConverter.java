package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.res.StoreMissionListDTO;
import com.example.umc9th.domain.mission.dto.res.StoreMissionResDTO;
import com.example.umc9th.domain.mission.entitiy.Mission;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class StoreMissionConverter {

    public static StoreMissionResDTO toDTO(Mission mission){
        return StoreMissionResDTO.builder()
                .missionId(mission.getId())
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .duration(mission.getDuration().toString())
                .build();
    }

    public static StoreMissionListDTO toListDTO(Page<Mission> missionPage){
        return StoreMissionListDTO.builder()
                .missions(
                        missionPage.getContent().stream()
                                .map(StoreMissionConverter::toDTO)
                                .collect(Collectors.toList())
                )
                .currentPage(missionPage.getNumber() + 1)
                .totalPage(missionPage.getTotalPages())
                .build();
    }
}
