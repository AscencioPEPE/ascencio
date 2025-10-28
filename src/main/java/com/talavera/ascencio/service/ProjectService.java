package com.talavera.ascencio.service;

import com.talavera.ascencio.model.Project;
import com.talavera.ascencio.model.User;
import com.talavera.ascencio.repo.ProjectRepository;
import com.talavera.ascencio.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private static final int FREE_QUOTA = 3;

    public List<Project> getProjects(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return projectRepository.findByOwner(user);
    }

    public Project createProject(String email, String name) {
        User user = userRepository.findByEmail(email).orElseThrow();
        long count = projectRepository.countByOwner(user);
        if (count >= FREE_QUOTA) {
            throw new RuntimeException("Quota exceeded (Free plan)");
        }

        Project project = Project.builder()
                .name(name)
                .owner(user)
                .build();
        return projectRepository.save(project);
    }

    public void deleteProject(Long id, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Project project = projectRepository.findById(id)
                .filter(p -> p.getOwner().equals(user))
                .orElseThrow(() -> new RuntimeException("Not found or not allowed"));
        projectRepository.delete(project);
    }
}
