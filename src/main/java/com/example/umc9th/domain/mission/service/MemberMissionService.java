package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.member.entitiy.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.mission.converter.MemberMissionConverter;
import com.example.umc9th.domain.mission.converter.MyMissionConverter;
import com.example.umc9th.domain.mission.dto.req.MemberMissionCreateReqDTO;
import com.example.umc9th.domain.mission.dto.res.MemberMissionCreateResDTO;
import com.example.umc9th.domain.mission.dto.res.MemberMissionResponse;
import com.example.umc9th.domain.mission.dto.res.MyMissionListDTO;
import com.example.umc9th.domain.mission.entitiy.Mission;
import com.example.umc9th.domain.mission.entitiy.mapping.MemberMission;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberMissionService {
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;

    public Page<MemberMissionResponse> getMyMissions(Long memberId, int page, int size) {
        var pageable = PageRequest.of(page, size);
        return memberMissionRepository.findeMemberMissionByMemeberId(memberId, pageable);
    }

    @Transactional
    public MemberMissionCreateResDTO challengeMission(Long missionId, MemberMissionCreateReqDTO request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(()->new GeneralException(GeneralErrorCode.NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(()->new GeneralException(GeneralErrorCode.NOT_FOUND));

        // 중복 도전 방지
        if(memberMissionRepository.existsByMemberIdAndMissionId(member.getId(), mission.getId())){
            throw new GeneralException(GeneralErrorCode.DUPLICATE_RESOUCE);
        }

        MemberMission memberMission = MemberMission.start(member, mission);
        MemberMission saved =  memberMissionRepository.save(memberMission);

        return MemberMissionConverter.toResDTO(saved);
    }

    public MyMissionListDTO getMyProgressMissions(Long memberId, int page){
        if(page<=0){
            throw new GeneralException(GeneralErrorCode.INVALID_PAGE);
        }
        var pageable = PageRequest.of(page-1 , 10);

        var missions = memberMissionRepository.findAllByMemberIdAndIsCompleteFalse(memberId, pageable);

        return MyMissionConverter.toMyMissionListDTO(missions);
    }
}
