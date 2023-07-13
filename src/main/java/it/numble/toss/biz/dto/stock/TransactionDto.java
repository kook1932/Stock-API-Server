package it.numble.toss.biz.dto.stock;

import it.numble.toss.biz.entity.stock.Transaction;
import it.numble.toss.biz.entity.stock.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private TransactionType type;
	private Long amount;
	private Long balance;
	private LocalDateTime createdAt;

	public static TransactionDto from(Transaction transaction) {
		return TransactionDto.builder()
				.type(transaction.getType())
				.amount(transaction.getAmount())
				.balance(transaction.getAmount())
				.build();
	}
}
