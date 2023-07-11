package it.numble.toss.biz.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "bank", length = 50)
	private String bank;

	@Column(name = "account_number", length = 50, unique = true)
	private String accountNumber;

	@Column(name = "balance")
	private Long balance;
}
