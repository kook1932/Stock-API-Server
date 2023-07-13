package it.numble.toss.biz.repository.stock;

import it.numble.toss.biz.entity.stock.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);

	List<Account> findByUserId(Long userId);

	@EntityGraph(attributePaths = "transactions")
	Optional<Account> findTransactionsById(Long id);
}
