package it.numble.toss.biz.controller;

import it.numble.toss.biz.dto.LoginDto;
import it.numble.toss.biz.dto.TokenDto;
import it.numble.toss.biz.entity.RefreshToken;
import it.numble.toss.biz.service.TokenService;
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

	private final TokenService tokenService;
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
	 * @param refreshToken
	 * @return ResponseEntity - accessToken
	 */
	@PostMapping("/reissue/access-token")
	public ResponseEntity<TokenDto> reissueAccessToken(@RequestBody String refreshToken) {
		String accessToken = tokenService.reissueAccessToken(refreshToken);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + accessToken);
		TokenDto tokenDto = TokenDto.builder()
				.accessToken(accessToken)
				.build();

		return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
	}

	//@PostMapping("/reissue/refresh-token")
}
