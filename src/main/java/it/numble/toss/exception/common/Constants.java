package it.numble.toss.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constants {

	@Getter @AllArgsConstructor
	public enum ExceptionClass {
		User("User"),
		Account("Account");

		private String exceptionClass;

		@Override
		public String toString() {
			return exceptionClass + " Exception.";
		}
	}
}
