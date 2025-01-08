package com.example.hnh.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

}
