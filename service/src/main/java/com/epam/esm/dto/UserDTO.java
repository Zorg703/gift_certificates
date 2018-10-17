package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

/**
 * The type User dto.
 */
public class UserDTO {
    private String userName;
    private List<TagDTO> tags;

    /**
     * Instantiates a new User dto.
     *
     * @param userName the user name
     * @param tags     the tags
     */
    public UserDTO(String userName, List<TagDTO> tags) {
        this.userName = userName;
        this.tags = tags;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public List<TagDTO> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(userName, userDTO.userName) &&
                Objects.equals(tags, userDTO.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, tags);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", tags=" + tags +
                '}';
    }
}
