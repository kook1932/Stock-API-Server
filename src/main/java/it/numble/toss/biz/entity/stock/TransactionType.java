package it.numble.toss.biz.entity.stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
	TRANSFORMATION("송금"),
	PAYMENT("결제"),
	DEPOSIT("입금");

	private final String nameKr;
}
