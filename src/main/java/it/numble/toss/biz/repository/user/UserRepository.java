package it.numble.toss.biz.repository.user;

import it.numble.toss.biz.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneAuthoritiesByUsername(String username);

	int deleteByUsername(String username);

}
