package dtos;

import entities.Developer;
import entities.Project;
import entities.ProjectHours;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProjectDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Set<ProjectHoursDTO> projectHours;

    public ProjectDTO() {
    }

    public ProjectDTO(Project project) {
        if (project.getId() != null)
            this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.projectHours = new HashSet<>();
        if (project.getProjectHours() != null)
            for (ProjectHours ph : project.getProjectHours()) {
                this.projectHours.add(new ProjectHoursDTO(ph));
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectHoursDTO> getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(Set<ProjectHoursDTO> projectHours) {
        this.projectHours = projectHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDTO entity = (ProjectDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description);
    }
}
