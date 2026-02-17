package com.smartdesk.hr.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.smartdesk.hr.dto.UserProvisioningDto;
import com.smartdesk.hr.service.HrUserService;
@RestController
@RequestMapping("/hr/users")
public class HrUserController {
 private final HrUserService service;
 public HrUserController(HrUserService service) {
  this.service = service;
 }
 @GetMapping("/new")
 public List<UserProvisioningDto> getNewUsers() {
  return service.getNewUsersForProvisioning();
 }
}