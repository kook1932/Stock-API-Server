package it.numble.toss.biz.service;

import it.numble.toss.biz.dto.AccountDto;
import it.numble.toss.biz.entity.Account;
import it.numble.toss.biz.repository.AccountRepository;
import it.numble.toss.exception.common.CommonException;
import it.numble.toss.exception.common.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final AccountRepository accountRepository;

	// TODO : 페이징(커서)
	@Transactional(readOnly = true)
	public List<AccountDto> findAllByUserId(Long userId) {
		return accountRepository.findByUserId(userId).stream()
				.map(AccountDto::from)
				.collect(Collectors.toList());
	}

	@Transactional(rollbackFor = CommonException.class)
	public Account saveAccount(AccountDto accountDto) throws CommonException {
		if (accountRepository.findByAccountNumber(accountDto.getAccountNumber()).orElse(null) != null) {
			throw new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "이미 존재하는 계좌번호입니다.");
		}

		Account newAccount = Account.builder()
				.userId(accountDto.getUserId())
				.bank(accountDto.getBank())
				.accountNumber(accountDto.getAccountNumber())
				.balance(accountDto.getBalance())
				.build();

		return accountRepository.save(newAccount);
	}

	@Transactional(rollbackFor = CommonException.class)
	public void delete(Long id) throws CommonException {
		if (accountRepository.findById(id).orElse(null) == null) {
			throw new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "존재하지 않는 계좌입니다.");
		}
		accountRepository.deleteById(id);
	}
}
