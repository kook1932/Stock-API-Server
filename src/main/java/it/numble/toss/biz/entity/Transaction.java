package it.numble.toss.biz.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {


	@Getter @RequiredArgsConstructor
	public enum Type {
		TRANSFORMATION("송금"),
		PAYMENT("결제");

		private final String nameKo;
	}
}
