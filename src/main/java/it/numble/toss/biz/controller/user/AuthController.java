package it.numble.toss.biz.controller.user;

import it.numble.toss.biz.dto.user.LoginDto;
import it.numble.toss.biz.dto.user.TokenDto;
import it.numble.toss.biz.entity.user.RefreshToken;
import it.numble.toss.biz.service.user.TokenService;
import it.numble.toss.biz.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static it.numble.toss.config.jwt.JwtFilter.AUTHORIZATION_HEADER;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

	private final TokenService tokenService;
	private final UserService userService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	/**
	 * ID, PW 기반으로 로그인하여 인증하는 API
	 * @param loginDto username, password
	 * @return ResponseEntity - TokenDto(accessToken, refreshToken)
	 */
	@PostMapping("/signin")
	public ResponseEntity<TokenDto> signin(@Valid @RequestBody LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto token = tokenService.createAllToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + token.getAccessToken());
		
		RefreshToken refreshToken = RefreshToken.builder()
				.id(loginDto.getUsername())
				.authorities(authentication.getAuthorities())
				.refreshToken(token.getRefreshToken())
				.build();
		tokenService.saveRefreshToken(refreshToken);

		return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
	}

	/**
	 * AccessToken 만료시 재발급하는 API
	 * @param tokenDto refreshToken
	 * @return Map - accessToken
	 */
	@PostMapping("/reissue/access-token")
	public ResponseEntity<Map<String, String>> reissueAccessToken(@RequestBody TokenDto tokenDto) {
		String accessToken = tokenService.reissueAccessToken(tokenDto.getRefreshToken());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + accessToken);

		Map<String, String> reissueToken = new HashMap<>();
		reissueToken.put("accessToken", accessToken);

		return new ResponseEntity<>(reissueToken, httpHeaders, HttpStatus.OK);
	}

}
