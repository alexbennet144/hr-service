package com.smartdesk.hr.service.user;

import com.yourorg.core.constant.enums.UserStatus;
import com.yourorg.core.constant.enums.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequestDto {

    @NotBlank(message = "tenantCode is mandatory")
    private String tenantCode;

    @NotBlank(message = "employeeId is mandatory")
    private String employeeId;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "displayName is mandatory")
    private String displayName;

    @NotNull(message = "userType is mandatory")
    private UserType userType;

    @NotNull(message = "status is mandatory")
    private UserStatus status;
}