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

	/* 송금 
	* 1. 기존 계좌에서 돈을 빼고, 잔액이 남는지 확인, 남는다면 잔액 변화, transaction 기록 
	* 2. 남지 않는다면 예외
	* 3. 수신자 계좌 조회 후, 잔액 플러스 */
	private boolean isOverBalance(Long amount) {
		return balance - amount > 0;
	}

	public Long removeBalance(TransferDto transferDto) throws CommonException {
		if (!isOverBalance(transferDto.getAmount())) {
			throw new CommonException(Constants.ExceptionClass.Account, HttpStatus.BAD_REQUEST, "계좌에 잔액이 부족합니다.");
		}
		balance -= transferDto.getAmount();
		return balance;
	}

	public Long addBalance(TransferDto transferDto) {
		balance += transferDto.getAmount();
		return balance;
	}

}
