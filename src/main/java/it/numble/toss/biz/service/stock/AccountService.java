package it.numble.toss.biz.service.stock;

import it.numble.toss.biz.dto.stock.AccountDto;
import it.numble.toss.biz.dto.stock.TransferDto;
import it.numble.toss.biz.entity.stock.Account;
import it.numble.toss.biz.entity.stock.Transaction;
import it.numble.toss.biz.entity.stock.TransactionType;
import it.numble.toss.biz.repository.stock.AccountRepository;
import it.numble.toss.biz.repository.stock.TransactionRepository;
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
	private final TransactionRepository transactionRepository;

	// TODO : 페이징(커서)
	@Transactional(readOnly = true)
	public List<AccountDto> findAllByUserId(Long userId) {
		return accountRepository.findByUserId(userId).stream()
				.map(AccountDto::from)
				.collect(Collectors.toList());
	}

	// TODO : 페이징(커서)
	@Transactional(readOnly = true)
	public AccountDto findAllByAccountIdWithTransactions(Long accountId) {
		return accountRepository.findTransactionsById(accountId)
				.map(AccountDto::fromWithTransactions)
				.orElse(null);
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

	@Transactional(rollbackFor = {CommonException.class, Exception.class})
	public Long transfer(Long tokenUserId, TransferDto transferDto) throws CommonException {
		// 1. accountId 로 계좌를 찾음
		Account myAccount = accountRepository.findById(transferDto.getAccountId())
				.orElseThrow(() -> new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "계좌가 존재하지 않습니다."));

		// 2. 입금자의 계좌가 맞는지 검증
		myAccount.isMyAccount(tokenUserId);

		// 3. receiverAccountNumber 계좌를 찾음
		Account receiverAccount = accountRepository.findByAccountNumber(transferDto.getReceiverAccountNumber())
				.orElseThrow(() -> new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "계좌가 존재하지 않습니다."));

		// 4. 송금
		Long myBalance = myAccount.removeBalance(transferDto.getAmount());
		Long receiverBalance = receiverAccount.addBalance(transferDto.getAmount());

		// 5. Transaction 기록(입금자, 수신자)
		Transaction myTransaction = Transaction.builder()
				.accountId(myAccount.getId())
				.type(TransactionType.TRANSFORMATION)
				.amount(transferDto.getAmount())
				.balance(myBalance)
				.build();

		Transaction receiverTransaction = Transaction.builder()
				.accountId(receiverAccount.getId())
				.type(TransactionType.DEPOSIT)
				.amount(transferDto.getAmount())
				.balance(receiverBalance)
				.build();

		transactionRepository.save(myTransaction);
		transactionRepository.save(receiverTransaction);

		return myBalance;
	}
}
