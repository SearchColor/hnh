package com.example.hnh.interestgroup;

import com.example.hnh.group.Group;
import com.example.hnh.group.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InterestGroupService {

    private final InterestGroupRepository interestGroupRepository;
    private final GroupRepository groupRepository;

    public InterestGroupService(InterestGroupRepository interestGroupRepository,
                                GroupRepository groupRepository) {
        this.interestGroupRepository = interestGroupRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * 관심 그룹 찜하기 API
     * @param groupId
     * @param userId
     * @return
     */
    public String toggleInterest(Long groupId, Long userId) {

        // 그룹 존재 여부 확인
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        // 관심 그룹 조회
        Optional<InterestGroup> existingInterest = interestGroupRepository.findByGroupIdAndUserId(groupId, userId);

        if (existingInterest.isEmpty()) {
            // 관심 그룹이 없으면 새로 생성
            InterestGroup interestGroup = new InterestGroup();
            interestGroup.setGroup(group);
            interestGroup.setUserId(userId);
            interestGroupRepository.save(interestGroup);
            return "좋아요를 눌렀습니다.";
        }

        // 관심 그룹이 존재하면 상태값 토글
        InterestGroup interestGroup = existingInterest.get();
        String newStatus = "active".equals(interestGroup.getStatus()) ? "deleted" : "active";
        interestGroup.setStatus(newStatus);
        interestGroupRepository.save(interestGroup);

        return "active".equals(newStatus) ? "좋아요를 눌렀습니다." : "좋아요를 취소했습니다.";

    }
}
