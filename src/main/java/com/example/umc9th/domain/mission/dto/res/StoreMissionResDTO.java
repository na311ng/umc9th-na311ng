package com.example.umc9th.domain.mission.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreMissionResDTO {
    private Long missionId;
    private String conditional;
    private Integer point;
    private String duration;
}
