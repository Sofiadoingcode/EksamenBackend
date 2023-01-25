package dtos;

import entities.Project;
import entities.ProjectHours;

import java.io.Serializable;
import java.util.Objects;

public class ProjectHoursDTO implements Serializable {

    private Long id;

    private double hoursSpent;

    private int userStory;

    private String description;

    public ProjectHoursDTO() {
    }

    public ProjectHoursDTO(ProjectHours projectHours) {
        if (projectHours.getId() != null)
            this.id = projectHours.getId();
        this.hoursSpent = projectHours.getHoursSpent();
        this.userStory = projectHours.getUserStory();
        this.description = projectHours.getDescription();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectHoursDTO entity = (ProjectHoursDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.hoursSpent, entity.hoursSpent) &&
                Objects.equals(this.userStory, entity.userStory) &&
                Objects.equals(this.description, entity.description);
    }

}
