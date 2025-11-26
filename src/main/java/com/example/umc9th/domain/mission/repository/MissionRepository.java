package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.res.MissionHomeResponse;
import com.example.umc9th.domain.mission.entitiy.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {


    @Query("""
        select new com.example.umc9th.domain.mission.dto.res.MissionHomeResponse(
            m.id,
            l.name,
            s.name,
            m.conditional,
            m.point,
            m.duration
        )
        from Mission m
        join m.store s
        join s.location l 
        where l.id = :locationId
        order by m.createdAt desc 
    """)
    Page<MissionHomeResponse> findMissionsByLocationId(
            @Param("locationId") Long locationId,
            Pageable pageable
    );

    // 특정 가게의 미션 목록 조회
    Page<Mission> findAllByStoreId(Long storeId, Pageable pageable);
}
