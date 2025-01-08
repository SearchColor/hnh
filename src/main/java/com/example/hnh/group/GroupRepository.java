package com.example.hnh.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

    default Group findByGroupOrElseThrow (Long groupId) {
        return findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("그룹을 찾을 수 없습니다.")
        );
    }
}
