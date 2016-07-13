package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
	User findOneByEmail(String email);
	User findOneByEmailAndActive(String email, Boolean active);
	User findOneByEmailAndEmailCode(String email, String code);
	User findById(Long id);

	Long countByEmail(String email);
	Long countByEmailAndActive(String email, Boolean active);
	Long countByNombre(String nombre);
	@Modifying
	@Query("UPDATE User u SET u.emailVerificado = true WHERE u.email = :email")
	void activate(@Param("email") String email);
}
