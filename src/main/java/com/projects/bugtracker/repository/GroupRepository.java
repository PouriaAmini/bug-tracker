package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT name FROM trackerdb.`group` WHERE " +
            "name LIKE '%:name%'", nativeQuery = true
    )
    List<Group> searchGroup(@Param("name") String name);
}
