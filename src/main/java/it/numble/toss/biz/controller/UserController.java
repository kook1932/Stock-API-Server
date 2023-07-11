package it.numble.toss.biz.controller;

import it.numble.toss.biz.dto.TokenDto;
import it.numble.toss.biz.dto.UserDto;
import it.numble.toss.biz.service.UserService;
import it.numble.toss.exception.common.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

	private final UserService userService;

	/**
	 * 회원가입 API
	 * @param userDto username, password
	 * @return ResponseEntity
	 */
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) throws CommonException {
		return ResponseEntity.ok(UserDto.from(userService.signup(userDto)));
	}

	/**
	 * 회원탈퇴 API
	 *
	 * @param userDto
	 */
	@DeleteMapping("/user")
	public ResponseEntity<Map<String, String>> delete(@Valid @RequestBody UserDto userDto) throws CommonException {
		userService.delete(userDto.getUsername());
		Map<String, String> result = new HashMap<>();
		result.put("status", "200");
		return ResponseEntity.ok(result);
	}

}
