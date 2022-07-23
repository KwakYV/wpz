package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value="FROM Message m WHERE m.devId = :devId", nativeQuery = true)
    List<Message> findAll(@Param("devId") long devId);


}
