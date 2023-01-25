package dtos;

import entities.Developer;
import entities.User;

import java.io.Serializable;
import java.util.Objects;

public class DeveloperDTO implements Serializable {

    private Long id;

    private String name;

    private String phone;

    private Double billingPrHour;

    private UserDTO userDTO;


    public DeveloperDTO () {

    }


    public DeveloperDTO(Developer developer) {
        if (developer.getId() != null)
            this.id = developer.getId();
        this.name = developer.getName();
        this.phone = developer.getPhone();
        this.billingPrHour = developer.getBillingPrHour();

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

    public Double getBillingPrHour() {
        return billingPrHour;
    }

    public void setBillingPrHour(double billingPrHour) {
        this.billingPrHour = billingPrHour;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDTO entity = (DeveloperDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.billingPrHour, entity.billingPrHour);
    }

}
