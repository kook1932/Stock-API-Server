package it.numble.toss.biz.service;

import it.numble.toss.biz.dto.stock.TransferDto;
import it.numble.toss.biz.entity.stock.Account;
import it.numble.toss.biz.entity.stock.Transaction;
import it.numble.toss.biz.entity.stock.TransactionType;
import it.numble.toss.biz.repository.stock.AccountRepository;
import it.numble.toss.biz.repository.stock.TransactionRepository;
import it.numble.toss.biz.service.stock.AccountService;
import it.numble.toss.exception.common.CommonException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionRepository transactionRepository;

	@InjectMocks
	AccountService accountService;

	private Long tokenUserId = 1L;

	private Account myAccount = Account.builder()
			.id(1L)
			.userId(1L)
			.bank("우리은행")
			.accountNumber("123456789")
			.balance(10000L)
			.build();

	private Account receiverAccount = Account.builder()
			.id(2L)
			.userId(2L)
			.bank("우리은행")
			.accountNumber("987654321")
			.balance(10000L)
			.build();

	@DisplayName("송금 기능 테스트")
	@Test
	void transferTest() throws CommonException {
		// given
		TransferDto transferDto = TransferDto.builder()
				.accountId(1L)
				.amount(7000L)
				.receiverAccountNumber(receiverAccount.getAccountNumber())
				.build();

		when(accountRepository.findById(transferDto.getAccountId())).thenReturn(Optional.of(myAccount));
		when(accountRepository.findByAccountNumber(transferDto.getReceiverAccountNumber())).thenReturn(Optional.of(receiverAccount));

		// when
		Long balance = accountService.transfer(tokenUserId, transferDto);

		// then
		Assertions.assertThat(balance).isEqualTo(3000L);
	}



}