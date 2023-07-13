package it.numble.toss.biz.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.numble.toss.biz.entity.stock.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@JsonIgnore
	private Long userId;

	@NotBlank
	private String bank;

	@NotBlank
	private String accountNumber;

	@PositiveOrZero
	private Long balance;

	private List<TransactionDto> transactions;

	public static AccountDto from(Account account) {
		if (account == null) return null;

		return AccountDto.builder()
				.id(account.getId())
				.bank(account.getBank())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.build();
	}

	public static AccountDto fromWithTransactions(Account account) {
		if (account == null) return null;

		return AccountDto.builder()
				.id(account.getId())
				.bank(account.getBank())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.transactions(account.getTransactions().stream()
						.map(TransactionDto::from)
						.collect(Collectors.toList()))
				.build();
	}

}
