package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
	User findOneByEmail(String email);
	User getUserByEmail(String email);
	User getUserByEmailCode(String code);
	User findOneByEmailAndEmailCode(String email, String code);

	Long countByEmail(String email);
	Long countByNombre(String nombre);
	@Modifying
	@Query("UPDATE User u SET u.emailVerificado = true WHERE u.email = :email")
	void activate(@Param("email") String email);
}
