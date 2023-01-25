package entities;

import dtos.ProjectDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "project")
@NamedQuery(name = "Project.deleteAllRows", query = "DELETE FROM Project")
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "project")
    private Set<ProjectHours> projectHours;

    @ManyToMany
    @JoinTable(
            name = "developer_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers = new LinkedHashSet<>();


    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Project(ProjectDTO projectDTO) {
        if (projectDTO.getId() != null)
            this.id = projectDTO.getId();
        this.name = projectDTO.getName();
        this.description = projectDTO.getDescription();

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

    public Set<ProjectHours> getProjectHours() {
        return projectHours;
    }

    public void addDeveloper(Developer developer) {
        if(developer != null) {
            developers.add(developer);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getName(), project.getName()) && Objects.equals(getDescription(), project.getDescription());
    }
}
