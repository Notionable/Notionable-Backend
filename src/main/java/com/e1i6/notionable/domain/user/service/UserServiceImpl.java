package com.e1i6.notionable.domain.user.service;import java.util.Optional;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import com.e1i6.notionable.domain.user.data.dto.request.EmailLoginReqDto;import com.e1i6.notionable.domain.user.data.dto.response.EmailLoginResDto;import com.e1i6.notionable.domain.user.entity.User;import com.e1i6.notionable.domain.user.repository.UserRepository;import com.e1i6.notionable.global.auth.JwtProvider;import com.e1i6.notionable.global.common.response.ResponseCode;import com.e1i6.notionable.global.common.response.ResponseException;import lombok.RequiredArgsConstructor;import lombok.extern.slf4j.Slf4j;@Service@Slf4j@RequiredArgsConstructorpublic class UserServiceImpl {	private final UserRepository userRepository;	private final PasswordEncoder passwordEncoder;	private final JwtProvider jwtProvider;	public EmailLoginResDto userLoginWithEmail(EmailLoginReqDto emailLoginReqDto) {		String email = emailLoginReqDto.getEmail();		String password = emailLoginReqDto.getPassword();		log.info("email: {}, password: {}", email, password);		Optional<User> userByEmail = userRepository.findUserByEmail(email);		if (userByEmail.isPresent()) {			User user = userByEmail.get();			// if (passwordEncoder.matches(password, user.getPassword())) {			if (password.equals(user.getPassword())) {				return EmailLoginResDto.builder()					.jwtDto(jwtProvider.generateToken(user.getUserId()))					.email(user.getEmail())					.nickName(user.getNickName())					.build();			} else {				log.info("email {}: password not matched", user.getEmail());				throw new ResponseException(ResponseCode.LOGIN_WITH_WRONG_PASSWORD);			}		} else {			log.info("email {}: no such email", email);			throw new ResponseException(ResponseCode.NOT_FOUND);		}	}}