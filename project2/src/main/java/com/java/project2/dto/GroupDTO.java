package com.java.project2.dto;

import com.java.project2.entity.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GroupDTO {
    //https://www.baeldung.com/jpa-many-to-many
    private Integer id;

    @NotBlank
    @Size(min=6)
    private String name;

    private List<UserDTO> users;
}
