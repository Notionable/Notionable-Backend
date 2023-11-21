package com.e1i6.notionable.domain.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e1i6.notionable.domain.user.data.dto.UserDto;
import com.e1i6.notionable.domain.user.data.dto.request.EmailLoginReqDto;
import com.e1i6.notionable.domain.user.data.dto.response.EmailLoginResDto;
import com.e1i6.notionable.domain.user.service.UserServiceImpl;
import com.e1i6.notionable.global.auth.JwtProvider;
import com.e1i6.notionable.global.common.response.BaseResponse;
import com.e1i6.notionable.global.common.response.ResponseCode;
import com.e1i6.notionable.global.common.response.ResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserServiceImpl userService;
	private final JwtProvider jwtProvider;

	@PostMapping("/login/email")
	public BaseResponse<EmailLoginResDto> loginWithEmail(@RequestBody EmailLoginReqDto emailLoginReqDto) {
		try {
			EmailLoginResDto emailLoginDto = userService.userLoginWithEmail(emailLoginReqDto);
			return new BaseResponse<>(emailLoginDto);
		} catch (ResponseException e) {
			return new BaseResponse<>(e.getErrorCode());
		} catch (Exception e) {
			return new BaseResponse<>(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping("/signup/complete")
	public BaseResponse<?> signUp(@RequestBody UserDto userDto) {
		try {
			UserDto resultUserDto = userService.signUp(userDto);
			return new BaseResponse<>(resultUserDto);
		} catch (ResponseException e) {
			return new BaseResponse<>(e.getErrorCode());
		} catch (Exception e) {
			return new BaseResponse<>(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
