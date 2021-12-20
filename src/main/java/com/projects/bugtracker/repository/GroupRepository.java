package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findGroupById(Long id);

    void deleteGroupById(Long id);

    @Query("SELECT group FROM Group group WHERE " +
            "group.name LIKE %?1%")
    List<Group> searchGroup(String name);
}
