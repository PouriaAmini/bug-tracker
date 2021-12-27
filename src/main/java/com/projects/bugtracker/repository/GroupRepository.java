package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findGroupById(UUID id);

    void deleteGroupById(UUID id);

    @Query("SELECT group FROM Group group WHERE " +
            "group.name LIKE ?1%")
    List<Group> searchGroup(String name);
}
