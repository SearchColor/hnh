package com.example.hnh.interestgroup;

import com.example.hnh.global.config.auth.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

