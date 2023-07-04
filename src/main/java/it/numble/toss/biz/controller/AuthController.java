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

	@PostMapping("/authenticate")
	public ResponseEntity<TokenDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);

		return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

	}
}
