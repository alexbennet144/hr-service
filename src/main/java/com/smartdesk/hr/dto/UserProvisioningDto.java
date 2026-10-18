package com.smartdesk.hr.dto;

import java.time.Instant;

import com.yourorg.core.constant.enums.UserStatus;
import com.yourorg.core.constant.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProvisioningDto {
	private String tenantCode;
	private String employeeId;
	private String email;
	private String displayName;
	private UserType userType;
	private UserStatus status;
	private Instant terminatedAt;
	private Instant eventTime;
}