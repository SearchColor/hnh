package com.example.hnh.interestgroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestGroupRepository extends JpaRepository<InterestGroup ,Long> {

    Optional<InterestGroup> findByGroupIdAndUserId(Long groupId, Long userId);
}
