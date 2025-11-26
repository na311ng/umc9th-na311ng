package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.res.MemberMissionResponse;
import com.example.umc9th.domain.mission.entitiy.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    Page<MemberMission> findAllByMemberIdAndIsCompleteFalse(Long memberId, Pageable pageable);

    @Query("""
select new com.example.umc9th.domain.mission.dto.res.MemberMissionResponse(
    mm.id,
    m.id,
    s.name,
    m.conditional,
    m.point,
    m.duration,
    mm.isComplete
)
from MemberMission mm
join mm.mission m
join m.store s
where mm.member.id = :memberId
and (:status is null or 
    (:status = 'ONGOING' and mm.isComplete = false) or
    (:status = 'COMPLETED' and mm.isComplete = true))
order by m.createdAt desc
""")
    Page<MemberMissionResponse> findeMemberMissionByMemeberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);
}
