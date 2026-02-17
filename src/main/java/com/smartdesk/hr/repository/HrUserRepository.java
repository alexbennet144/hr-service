package com.smartdesk.hr.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartdesk.hr.constant.enums.UserStatus;
import com.smartdesk.hr.entity.HrUserEntity;
public interface HrUserRepository extends JpaRepository<HrUserEntity, Long> {
 List<HrUserEntity> findByStatus(UserStatus status);
}