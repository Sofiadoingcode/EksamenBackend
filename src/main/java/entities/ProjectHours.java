package entities;

import dtos.ProjectHoursDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "projecthours")
@NamedQuery(name = "ProjectHours.deleteAllRows", query = "DELETE FROM ProjectHours")
public class ProjectHours {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double hoursSpent;

    private int userStory;

    private String description;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectHours() {
    }

    public ProjectHours(double hoursSpent, int userStory, String description) {
        this.hoursSpent = hoursSpent;
        this.userStory = userStory;
        this.description = description;
    }

    public ProjectHours(double hoursSpent, int userStory, String description, Developer developer, Project project) {
        this.hoursSpent = hoursSpent;
        this.userStory = userStory;
        this.description = description;
        this.developer = developer;
        this.project = project;
    }

    public ProjectHours(ProjectHoursDTO projectHoursDTO) {
        if (projectHoursDTO.getId() != null)
            this.id = projectHoursDTO.getId();
        this.hoursSpent = projectHoursDTO.getHoursSpent();
        this.userStory = projectHoursDTO.getUserStory();
        this.description = projectHoursDTO.getDescription();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public int getUserStory() {
        return userStory;
    }

    public void setUserStory(int userStory) {
        this.userStory = userStory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectHours)) return false;
        ProjectHours ph = (ProjectHours) o;
        return Objects.equals(getHoursSpent(), ph.getHoursSpent()) && Objects.equals(getDescription(), ph.getDescription());
    }



}
