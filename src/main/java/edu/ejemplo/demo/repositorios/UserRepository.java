package edu.ejemplo.demo.repositorios;

import org.springframework.data.repository.CrudRepository;

import edu.ejemplo.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User getUserByEmail(String email);
	public User getUserByEmailCode(String code);
}
