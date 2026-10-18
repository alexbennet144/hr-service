package com.smartdesk.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartdesk.hr.entity.HrUserEntity;
import com.yourorg.core.constant.enums.UserStatus;

public interface HrUserRepository extends JpaRepository<HrUserEntity, Long> {
	List<HrUserEntity> findByStatus(UserStatus status);
}