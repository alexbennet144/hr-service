package com.smartdesk.hr.service.user;

import lombok.Data;

import java.time.Instant;

import com.yourorg.core.constant.enums.UserStatus;
import com.yourorg.core.constant.enums.UserType;

@Data
public class UserResponseDto {
    private Long id;
    private String tenantCode;
    private String employeeId;
    private String email;
    private String displayName;
    private UserType userType;
    private UserStatus status;
    private Instant terminatedAt;
    private Instant createdAt;
    private Instant updatedAt;
}