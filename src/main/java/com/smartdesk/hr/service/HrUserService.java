package com.smartdesk.hr.service;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.smartdesk.hr.constant.enums.UserStatus;
import com.smartdesk.hr.dto.UserProvisioningDto;
import com.smartdesk.hr.entity.HrUserEntity;
import com.smartdesk.hr.repository.HrUserRepository;
@Service
public class HrUserService {
 private final HrUserRepository repository;
 public HrUserService(HrUserRepository repository) {
  this.repository = repository;
 }
 @Transactional(readOnly = true)
 public List<UserProvisioningDto> getNewUsersForProvisioning() {
  return repository.findByStatus(UserStatus.NEW)
   .stream()
   .map(this::mapToDto)
   .toList();
 }
 private UserProvisioningDto mapToDto(HrUserEntity user) {
  UserProvisioningDto dto = new UserProvisioningDto();
  dto.setTenantCode(user.getTenantCode());
  dto.setEmployeeId(user.getEmployeeId());
  dto.setEmail(user.getEmail());
  dto.setDisplayName(user.getDisplayName());
  dto.setUserType(user.getUserType());
  dto.setStatus(user.getStatus());
  dto.setTerminatedAt(user.getTerminatedAt());
  dto.setEventTime(Instant.now());
  return dto;
 }
}