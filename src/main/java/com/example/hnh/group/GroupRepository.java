package com.example.hnh.group;

import com.example.hnh.user.dto.DashboardResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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
            "count (member.id), count (b.id), count (i.id), count (meet.id), :groupName) " +
            "FROM Group g JOIN g.boards b JOIN g.interestGroups i JOIN g.meets meet JOIN g.members member " +
            "WHERE g.name = :groupName AND g.createdAt BETWEEN :startDate AND :endDate")
    DashboardResponseDto findStatsByName(@Param("startDate") LocalDateTime start,
                                         @Param("endDate") LocalDateTime end,
                                         @Param("groupName") String groupName);
}
