package it.numble.toss.biz.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

	private String accessToken;
	private String refreshToken;

}
