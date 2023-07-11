package it.numble.toss.biz.controller;

import it.numble.toss.biz.dto.AccountDto;
import it.numble.toss.biz.service.AccountService;
import it.numble.toss.config.jwt.TokenProvider;
import it.numble.toss.exception.common.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.numble.toss.config.jwt.JwtFilter.AUTHORIZATION_HEADER;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

	private final HttpServletRequest request;
	private final TokenProvider tokenProvider;
	private final AccountService accountService;

	@GetMapping("/account")
	public ResponseEntity<Map<String, List<AccountDto>>> findAllAccount() {
		Long userId = tokenProvider.getUserId(request.getHeader(AUTHORIZATION_HEADER));
		List<AccountDto> accounts = accountService.findAllByUserId(userId);

		Map<String, List<AccountDto>> responseBody = new HashMap<>();
		responseBody.put("accounts", accounts);

		return ResponseEntity.ok(responseBody);
	}

	@PostMapping("/account")
	public ResponseEntity<AccountDto> saveAccount(@Valid @RequestBody AccountDto accountDto) throws CommonException {
		Long userId = tokenProvider.getUserId(request.getHeader(AUTHORIZATION_HEADER));
		accountDto.setUserId(userId);

		return ResponseEntity.ok(AccountDto.from(accountService.saveAccount(accountDto)));
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) throws CommonException {
		accountService.delete(id);
		Map<String, String> result = new HashMap<>();
		result.put("status", "200");
		return ResponseEntity.ok(result);
	}
}
