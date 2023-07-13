package it.numble.toss.biz.dto.stock;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

	private Long accountId;

	@Positive
	private Long amount;

	@NotEmpty
	private String receiverAccountNumber;
}
