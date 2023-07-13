package it.numble.toss.biz.entity.stock;

import lombok.*;

import javax.persistence.*;

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

	@Enumerated(EnumType.STRING)
	@Column
	private TransactionType type;

	@Column
	private Long amount;

	@Column
	private Long balance;
}
