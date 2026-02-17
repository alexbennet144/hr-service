package com.smartdesk.hr.entity;
import java.time.Instant;
import com.smartdesk.hr.constant.enums.UserStatus;
import com.smartdesk.hr.constant.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "hr_user")
@Getter
@Setter
public class HrUserEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column(name = "tenant_code", nullable = false)
 private String tenantCode;
 @Column(name = "employee_id", nullable = false, unique = true)
 private String employeeId;
 @Column(nullable = false, unique = true)
 private String email;
 @Column(name = "display_name", nullable = false)
 private String displayName;
 @Enumerated(EnumType.STRING)
 @Column(name = "user_type", nullable = false)
 private UserType userType;
 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 private UserStatus status;
 @Column(name = "terminated_at")
 private Instant terminatedAt;
 @Column(name = "created_at")
 private Instant createdAt;
 @Column(name = "updated_at")
 private Instant updatedAt;
 @PrePersist
 void onCreate() {
  createdAt = Instant.now();
  updatedAt = createdAt;
 }
 @PreUpdate
 void onUpdate() {
  updatedAt = Instant.now();
 }
}