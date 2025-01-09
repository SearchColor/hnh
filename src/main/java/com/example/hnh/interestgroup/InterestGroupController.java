package com.example.hnh.interestgroup;

import com.example.hnh.global.config.auth.UserDetailsImpl;
import com.example.hnh.interestgroup.dto.InterestGroupResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class InterestGroupController {

    private final InterestGroupService interestGroupService;

    public InterestGroupController(InterestGroupService interestGroupService) {
        this.interestGroupService = interestGroupService;
    }

    /**
     * 관심 그룹 찜하기 API
     * @param groupId
     * @param userDetails
     * @return
     */
    @PostMapping("/{groupId}/interest")
    public ResponseEntity<String> toggleInterest(
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        String message = interestGroupService.toggleInterest(groupId, userId);

        return ResponseEntity.ok(message);
    }

    /**
     * 관심 그룹 목록 조회 API
     * @param userDetails
     * @return
     */
    @GetMapping("/interest")
    public ResponseEntity<List<InterestGroupResponseDto>> getUserInterestGroups(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        List<InterestGroupResponseDto> interestGroups = interestGroupService.getUserInterestGroups(userId);

        return ResponseEntity.ok(interestGroups);
    }

}

