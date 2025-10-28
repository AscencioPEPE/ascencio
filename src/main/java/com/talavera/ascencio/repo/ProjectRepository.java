package com.talavera.ascencio.repo;

import com.talavera.ascencio.model.Project;
import com.talavera.ascencio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User user);
    long countByOwner(User user);
}
