package it.numble.toss.biz.controller;

import it.numble.toss.biz.dto.LoginDto;
import it.numble.toss.biz.dto.TokenDto;
import it.numble.toss.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
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

import static it.numble.toss.config.jwt.JwtFilter.AUTHORIZATION_HEADER;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	/**
	 * ID, PW 기반으로 로그인하여 인증하는 API<br>
	 * @param loginDto username, password
	 * @return ResponseEntity - accessToken, refreshToken
	 */
	@PostMapping("/signin")
	public ResponseEntity<TokenDto> signin(@Valid @RequestBody LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = tokenProvider.createRefreshToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + accessToken);

		return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
	}

	/**
	 * AccessToken 만료시 재발급하는 API
	 * @param refreshToken
	 * @return ResponseEntity - accessToken
	 */
	@PostMapping("/reissue/access-token")
	public ResponseEntity<TokenDto> reissueAccessToken(@RequestBody String refreshToken) {
		// token 유효성 검증은 JwtFilter 에서 실행
		// user db 에서 refresh token 값 조회
		return null;
	}

	//@PostMapping("/reissue/refresh-token")
}
