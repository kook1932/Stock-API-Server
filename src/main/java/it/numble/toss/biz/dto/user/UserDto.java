package it.numble.toss.biz.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.numble.toss.biz.entity.user.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	@Size(min = 3, max = 100)
	private String password;

	@Pattern(regexp = "\\d{8}")
	private String birthDay;

	private Set<AuthorityDto> authorityDtoSet;

	public static UserDto from(User user) {
		if(user == null) return null;

		return UserDto.builder()
				.username(user.getUsername())
				.birthDay(user.getBirthDay())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(authority -> new AuthorityDto(authority.getAuthorityName()))
						.collect(Collectors.toSet()))
				.build();
	}

}
