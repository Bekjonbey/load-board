package com.example.transaction2.telegramBot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findFirstByGroupId(String groupId);
    List<Group> findAllByDeletedIs(Boolean deleted);
}
