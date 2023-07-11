package it.numble.toss.biz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.numble.toss.biz.entity.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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

	@Positive
	private Long balance;

	public static AccountDto from(Account account) {
		if (account == null) return null;

		return AccountDto.builder()
				.id(account.getId())
				.bank(account.getBank())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.build();
	}

}
