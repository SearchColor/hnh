package com.example.hnh.meetmember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetMemberRepository extends JpaRepository<MeetMember, Long> {

    boolean existsByMemberIdAndMeetId(Long id, Long meetId);

}
