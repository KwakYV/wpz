package ru.wpz.lessons.lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.lessons.lesson1.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
