package it.numble.toss.biz.repository;

import it.numble.toss.biz.entity.user.RefreshToken;
import it.numble.toss.biz.repository.user.RefreshTokenRedisRepository;
import it.numble.toss.config.redis.EmbeddedRedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

@Import(EmbeddedRedisConfig.class)
@DataRedisTest
class RefreshTokenRedisRepositoryTest {

	@Autowired
	private RefreshTokenRedisRepository refreshTokenRedisRepository;

	@DisplayName("Embedded Redis Test")
	@Test
	void saveTest() {
		RefreshToken refreshToken = RefreshToken.builder()
				.id("admin")
				.refreshToken("refreshToken")
				.build();

		refreshTokenRedisRepository.save(refreshToken);

		RefreshToken token = refreshTokenRedisRepository.findByRefreshToken("refreshToken").orElseThrow();

		Assertions.assertThat(token.getId()).isEqualTo("admin");
		Assertions.assertThat(token.getRefreshToken()).isEqualTo("refreshToken");
		System.out.println("id = " + token.getId());
		System.out.println("token = " + token.getRefreshToken());
	}

}