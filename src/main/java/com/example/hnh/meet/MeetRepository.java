package com.example.hnh.meet;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {

    // 그룹 ID 기준으로 모든 모임 조회
    List<Meet> findAllByGroupId(Long groupId);

}
