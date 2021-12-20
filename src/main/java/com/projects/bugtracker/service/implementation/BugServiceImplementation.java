package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.repository.BugRepository;
import com.projects.bugtracker.service.BugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BugServiceImplementation implements BugService {

    private final BugRepository bugRepository;

    @Override
    public Bug get(UUID id) {
        log.info("Bug: FETCHING ID {}", id);
        return bugRepository.findBugById(id).orElseThrow(
                () -> new EntityNotFoundException("NO BUG FOUND WITH ID: " + id)
        );
    }

    @Override
    public Bug create(Bug bug) {
        log.info("Bug: CREATE NAME {}", bug.getName());
        return bugRepository.save(bug);
    }

    @Override
    public Bug update(Bug bug) {
        return null;
    }

    @Override
    public Boolean delete(Bug bug) {
        return null;
    }

    @Override
    public List<Bug> searchBug(String name) {
        return null;
    }
}
