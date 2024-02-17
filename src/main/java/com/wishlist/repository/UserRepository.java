package net.xindus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.xindus.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
