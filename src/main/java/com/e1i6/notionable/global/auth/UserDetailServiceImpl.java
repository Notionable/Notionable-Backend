package com.e1i6.notionable.global.auth;import java.util.Optional;import org.springframework.security.core.authority.AuthorityUtils;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.e1i6.notionable.domain.user.entity.Role;import com.e1i6.notionable.domain.user.entity.User;import com.e1i6.notionable.domain.user.repository.UserRepository;import lombok.RequiredArgsConstructor;@Service@RequiredArgsConstructor@Transactional(readOnly = true)public class UserDetailServiceImpl implements UserDetailsService {	private final UserRepository userRepository;	@Override	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {		Optional<User> userInfo = userRepository.findById(Long.parseLong(userId));		if (userInfo.isPresent()) {			User user = userInfo.get();			return org.springframework.security.core.userdetails.User				.withUsername(userId)				.password(user.getPassword())				.authorities(AuthorityUtils.createAuthorityList(Role.ROLE_USER.value()))				.build();		}		return null;	}}