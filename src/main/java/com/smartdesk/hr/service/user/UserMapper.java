package com.smartdesk.hr.service.user;

import org.springframework.stereotype.Component;

import com.smartdesk.hr.entity.HrUserEntity;

@Component
public class UserMapper {

    public HrUserEntity toEntity(CreateUserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        HrUserEntity entity = new HrUserEntity();
        entity.setTenantCode(dto.getTenantCode());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setEmail(dto.getEmail());
        entity.setDisplayName(dto.getDisplayName());
        entity.setUserType(dto.getUserType());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public UserResponseDto toResponseDto(HrUserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setTenantCode(entity.getTenantCode());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmail(entity.getEmail());
        dto.setDisplayName(entity.getDisplayName());
        dto.setUserType(entity.getUserType());
        dto.setStatus(entity.getStatus());
        dto.setTerminatedAt(entity.getTerminatedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}