package entities;

import dtos.DeveloperDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developer")
@NamedQuery(name = "Developer.deleteAllRows", query = "DELETE FROM Developer")
public class Developer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private Double billingPrHour;


    @OneToMany(mappedBy = "developer")
    private Set<ProjectHours> projectHours;

    @ManyToMany
    @JoinTable(
            name = "developer_project",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new LinkedHashSet<>();

    @OneToOne(mappedBy = "developer")
    private User user;

    public Developer() {
    }

    public Developer(String name, String phone, Double billingPrHour) {
        this.name = name;
        this.phone = phone;
        this.billingPrHour = billingPrHour;
        this.user = user;
    }

    public Developer(DeveloperDTO developerDTO) {
        if (developerDTO.getId() != null)
            this.id = developerDTO.getId();
        this.name = developerDTO.getName();
        this.phone = developerDTO.getPhone();
        this.billingPrHour = developerDTO.getBillingPrHour();
        this.user = developerDTO.getUser();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBillingPrHour() {
        return billingPrHour;
    }

    public void setBillingPrHour(Double billingPrHour) {
        this.billingPrHour = billingPrHour;
    }

    public Set<ProjectHours> getProjectHours() {
        return projectHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProject(Project project) {
        if(project != null) {
            projects.add(project);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer)) return false;
        Developer developer = (Developer) o;
        return Objects.equals(getName(), developer.getName()) && Objects.equals(getPhone(), developer.getPhone());
    }

}
