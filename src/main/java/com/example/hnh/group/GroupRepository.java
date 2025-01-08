package com.example.hnh.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

    default Group findByGroupOrElseThrow (Long groupId) {
        return findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("그룹을 찾을 수 없습니다.")
        );
    }

//    @Query("select new com.example.hnh.user.dto.DashboardResponseDto("+" count )" +
//            "from Group g " +
//            "join g.Member m " +
//            ")
//    DashboardResponseDto findStatsByName(@Param("startDate") LocalDate start,
//                                         @Param("endDate") LocalDate end,
//                                         @Param("groupName") String groupName);
}
