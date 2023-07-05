package it.numble.toss.biz.repository;

import it.numble.toss.biz.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

	Optional<RefreshToken> findByRefreshToken(String refreshToken);

}