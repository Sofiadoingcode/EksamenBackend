package dtos;

import entities.Developer;
import entities.Project;
import entities.User;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private String userName;

    private  DeveloperDTO developerDTO;

    public UserDTO(String userName, DeveloperDTO developerDTO) {
        this.userName = userName;
        this.developerDTO = developerDTO;

    }

    public UserDTO(User user) {
        if (user.getUserName() != null)
            this.userName = user.getUserName();
        if (user.getDeveloper() != null)
            this.developerDTO = new DeveloperDTO(user.getDeveloper());

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DeveloperDTO getDeveloperDTO() {
        return developerDTO;
    }

    public void setDeveloperDTO(DeveloperDTO developerDTO) {
        this.developerDTO = developerDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.userName, entity.userName);
    }
}
