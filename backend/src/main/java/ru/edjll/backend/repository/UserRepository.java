package ru.edjll.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.backend.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsernameAndIdNot(String username, String id);

    Boolean existsByEmailAndIdNot(String username, String id);
}
