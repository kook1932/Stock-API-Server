package it.numble.toss.biz.repository.stock;

import it.numble.toss.biz.entity.stock.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
