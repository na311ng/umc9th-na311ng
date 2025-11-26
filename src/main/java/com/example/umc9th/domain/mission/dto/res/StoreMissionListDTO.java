package com.example.umc9th.domain.mission.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreMissionListDTO {
    private List<StoreMissionResDTO> missions;
    private int currentPage;
    private int totalPage;

}
