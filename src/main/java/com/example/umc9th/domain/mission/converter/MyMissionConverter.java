package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.res.MyMissionListDTO;
import com.example.umc9th.domain.mission.entitiy.mapping.MemberMission;
import org.springframework.data.domain.Page;

public class MyMissionConverter {

    public static MyMissionListDTO toMyMissionListDTO(Page<MemberMission> page){

        var missions = page.getContent().stream()
                .map(mm -> MyMissionListDTO.MyMissionDTO.builder()
                        .missionId(mm.getMission().getId())
                        .storeName(mm.getMission().getStore().getName())
                        .conditional(mm.getMission().getConditional())
                        .point(mm.getMission().getPoint())
                        .duration(mm.getMission().getDuration())
                        .isComplete(mm.isComplete())
                        .build())
                .toList();
        return MyMissionListDTO.builder()
                .missions(missions)
                .currentPage(page.getNumber()+1)
                .totalPages(page.getTotalPages())
                .build();

    }
}
