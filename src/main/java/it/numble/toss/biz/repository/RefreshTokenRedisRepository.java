package it.numble.toss.biz.repository;

import it.numble.toss.biz.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

	RefreshToken findByRefreshToken(String refreshToken);

}