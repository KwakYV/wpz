package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.wpz.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Mono<User> findByUsername(String name);
}
