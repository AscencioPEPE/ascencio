package com.talavera.ascencio.service;

import com.talavera.ascencio.model.Project;
import com.talavera.ascencio.model.User;
import com.talavera.ascencio.repo.ProjectRepository;
import com.talavera.ascencio.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = User.builder().id(1L).email("u@mail.com").build();
    }

    @Test
    void createProject_ShouldSave_WhenUnderQuota() {
        when(userRepository.findByEmail("u@mail.com")).thenReturn(Optional.of(mockUser));
        when(projectRepository.countByOwner(mockUser)).thenReturn(2L);
        when(projectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Project result = projectService.createProject("u@mail.com", "Test Project");

        assertEquals("Test Project", result.getName());
        assertEquals(mockUser, result.getOwner());
    }

    @Test
    void createProject_ShouldThrow_WhenOverQuota() {
        when(userRepository.findByEmail("u@mail.com")).thenReturn(Optional.of(mockUser));
        when(projectRepository.countByOwner(mockUser)).thenReturn(5L);

        assertThrows(RuntimeException.class,
                () -> projectService.createProject("u@mail.com", "OverLimit"));
    }

    @Test
    void getProjects_ShouldReturnList() {
        when(userRepository.findByEmail("u@mail.com")).thenReturn(Optional.of(mockUser));
        when(projectRepository.findByOwner(mockUser))
                .thenReturn(List.of(new Project(1L, "A", mockUser), new Project(2L, "B", mockUser)));

        List<Project> projects = projectService.getProjects("u@mail.com");

        assertEquals(2, projects.size());
        assertEquals("A", projects.get(0).getName());
    }
}
