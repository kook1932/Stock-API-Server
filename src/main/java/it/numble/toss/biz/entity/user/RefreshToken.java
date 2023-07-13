package it.numble.toss.biz.entity.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "refresh", timeToLive = 604800)
public class RefreshToken implements Serializable {

	@Id
	private String id;
	private String ip;
	private Collection<? extends GrantedAuthority> authorities;

	@Indexed
	private String refreshToken;

}
