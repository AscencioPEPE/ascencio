package com.talavera.ascencio.controller;

import com.talavera.ascencio.model.Project;
import com.talavera.ascencio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAll(@RequestParam String email) {
        return projectService.getProjects(email);
    }

    @PostMapping
    public Project create(@RequestParam String email, @RequestParam String name) {
        return projectService.createProject(email, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam String email) {
        projectService.deleteProject(id, email);
    }
}
