package com.example.hnh.meetmember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface MeetMemberRepository extends JpaRepository<MeetMember, Long> {

    boolean existsByMemberIdAndMeetId(Long id, Long meetId);

    List<MeetMember> findByMeetId(Long meetId);
}
