package it.numble.toss.biz.entity.stock;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "account_id")
	private Long accountId;

	@Enumerated(EnumType.STRING)
	@Column
	private TransactionType type;

	@Column
	private Long amount;

	@Column
	private Long balance;

	@CreatedDate
	private LocalDateTime createdAt;
}
