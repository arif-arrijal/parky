package edu.ejemplo.demo.repositorios;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
	User getUserByEmail(String email);
	User getUserByEmailCode(String code);
	User findOneByEmailAndEmailCode(String email, String code);
	@Modifying
	@Query("UPDATE User u SET u.emailVerificado = true WHERE u.email = :email")
	void activate(@Param("email") String email);
}
