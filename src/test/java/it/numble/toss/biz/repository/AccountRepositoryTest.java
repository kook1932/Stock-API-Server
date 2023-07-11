package it.numble.toss.biz.repository;

import it.numble.toss.biz.entity.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepository;

	@DisplayName("사용자 계좌 목록 조회")
	@Test
	void findByIdTest() {
		Account newAccount = Account.builder()
				.userId(1L)
				.bank("우리은행")
				.accountNumber("1002-100-123456")
				.balance(1000L)
				.build();

		Account saved = accountRepository.save(newAccount);
		List<Account> accounts = accountRepository.findByUserId(1L);

		Assertions.assertThat(accounts.size()).isEqualTo(1);
	}
}