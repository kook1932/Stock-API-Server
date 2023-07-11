package it.numble.toss.biz.service;

import it.numble.toss.biz.dto.UserDto;
import it.numble.toss.biz.entity.Authority;
import it.numble.toss.biz.entity.User;
import it.numble.toss.biz.repository.UserRepository;
import it.numble.toss.exception.common.CommonException;
import it.numble.toss.exception.common.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User signup(UserDto userDto) throws CommonException {
		if (userRepository.findOneAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new CommonException(Constants.ExceptionClass.User, HttpStatus.BAD_REQUEST, "이미 가입되어 있는 사용자입니다.");
		}

		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();

		User user = User.builder()
				.username(userDto.getUsername())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.birthDay(userDto.getBirthDay())
				.authorities(Collections.singleton(authority))
				.build();

		return userRepository.save(user);
	}

	@Transactional
	public void delete(String username) throws CommonException {
		if (userRepository.deleteByUsername(username) == 0) {
			throw new CommonException(Constants.ExceptionClass.User, HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.");
		}
	}

}
