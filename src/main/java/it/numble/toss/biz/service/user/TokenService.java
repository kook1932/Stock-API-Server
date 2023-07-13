package it.numble.toss.biz.service.user;

import it.numble.toss.biz.dto.user.TokenDto;
import it.numble.toss.biz.entity.user.RefreshToken;
import it.numble.toss.biz.repository.user.RefreshTokenRedisRepository;
import it.numble.toss.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final TokenProvider tokenProvider;

	@Transactional
	public TokenDto createAllToken(Authentication authentication) {
		return TokenDto.builder()
				.accessToken(tokenProvider.createAccessToken(authentication))
				.refreshToken(tokenProvider.createRefreshToken(authentication))
				.build();
	}

	@Transactional
	public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
		return refreshTokenRedisRepository.save(refreshToken);
	}


	@Transactional
	public String reissueAccessToken(String refreshTokenJwt) {
		RefreshToken refreshToken = refreshTokenRedisRepository.findByRefreshToken(refreshTokenJwt)
				.orElseThrow(() -> new RuntimeException("refresh Token 이 존재하지 않습니다"));

		if (refreshToken != null && tokenProvider.validateToken(refreshToken.getRefreshToken())) {
			return tokenProvider.reissueAccessToken(refreshToken.getId(), refreshToken.getAuthorities());
		}
		return null;
	}

}
