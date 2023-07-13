package it.numble.toss.biz.controller.stock;

import it.numble.toss.biz.dto.stock.AccountDto;
import it.numble.toss.biz.dto.stock.TransferDto;
import it.numble.toss.biz.service.stock.AccountService;
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

	// TODO : token userId 추출 추상화 -> annotation
	@PostMapping("/transfer")
	public ResponseEntity<Map<String, Long>> transfer(@Valid @RequestBody TransferDto transferDto) throws CommonException {
		Long tokenUserId = tokenProvider.getUserId(request.getHeader(AUTHORIZATION_HEADER));

		Map<String, Long> responseBody = new HashMap<>();
		responseBody.put("balance", accountService.transfer(tokenUserId, transferDto));
		return ResponseEntity.ok(responseBody);
	}
}
