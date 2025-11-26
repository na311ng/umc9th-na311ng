package com.example.umc9th.domain.mission.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyMissionListDTO {
    private List<MyMissionDTO> missions;
    private int currentPage;
    private int totalPages;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyMissionDTO{
        private Long missionId;
        private String storeName;
        private String conditional;
        private Integer point;
        private LocalDate duration;
        private Boolean isComplete;
    }
}

