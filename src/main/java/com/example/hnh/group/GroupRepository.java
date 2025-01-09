package com.example.hnh.group;

import com.example.hnh.user.dto.DashboardResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

    default Group findByGroupOrElseThrow (Long groupId) {
        return findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("그룹을 찾을 수 없습니다.")
        );
    }

    /**
     * 관리자 통계 쿼리
     *
     * @param start 조회 기간 시작 날짜
     * @param end 조회 기간 마지막 날짜
     * @param groupName 조회 그룹 이름
     * @return
     */
    @Query("SELECT new com.example.hnh.user.dto.DashboardResponseDto(" +
            "count (g.members), count (g.boards), count (g.interestGroups), count (g.meets), :groupName) " +
            "FROM Group g JOIN FETCH g.boards JOIN FETCH g.interestGroups JOIN FETCH g.meets JOIN FETCH g.members " +
            "WHERE g.name = :groupName AND g.createdAt BETWEEN :start AND :end")
    DashboardResponseDto findStatsByName(@Param("startDate") LocalDate start,
                                         @Param("endDate") LocalDate end,
                                         @Param("groupName") String groupName);
}
