package com.smartdesk.hr.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartdesk.hr.dto.UserProvisioningDto;
import com.smartdesk.hr.entity.HrUserEntity;
import com.smartdesk.hr.service.user.CreateUserRequestDto;
import com.smartdesk.hr.service.user.HrUserService;
import com.smartdesk.hr.service.user.UserMapper;
import com.smartdesk.hr.service.user.UserResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/hr/users")
@RequiredArgsConstructor
public class HrUserController {
	private final HrUserService service;
	private final UserMapper userMapper;

	@GetMapping("/new")
	public List<UserProvisioningDto> getNewUsers() {
		return null;
	}

	@PostMapping("/new")
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) throws Exception {
		HrUserEntity userToCreate = userMapper.toEntity(requestDto);
		HrUserEntity createdUser = service.createUser(userToCreate);
		UserResponseDto responseDto = userMapper.toResponseDto(createdUser);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId())
				.toUri();

		return ResponseEntity.created(location).body(responseDto);
	}
}