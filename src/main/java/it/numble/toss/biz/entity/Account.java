package it.numble.toss.biz.entity;

import it.numble.toss.biz.dto.TransferDto;
import it.numble.toss.exception.common.CommonException;
import it.numble.toss.exception.common.Constants;
import lombok.*;
import org.springframework.http.HttpStatus;

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

	public Long removeBalance(Long amount) throws CommonException {
		if (!isOverBalance(amount)) {
			throw new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "계좌에 잔액이 부족합니다.");
		}
		balance -= amount;
		return balance;
	}

	public Long addBalance(Long amount) {
		balance += amount;
		return balance;
	}

	// token 내 userId 와 account 내 userId 검증
	public void isMyAccount(Long tokenUserId) throws CommonException {
		if (tokenUserId != userId)
			throw new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "계좌 정보가 일치하지 않습니다.");
	}

	private boolean isOverBalance(Long amount) {
		return balance - amount >= 0;
	}

}
