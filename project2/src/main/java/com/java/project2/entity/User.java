package com.java.project2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @Column(name = "avatar")
    private String avatar;// URL

    @Column(unique = true)
    private String username;

    //@JsonProperty("password")
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    //do dung chung 1 class giua entity va model
    //anotation nay se ko luu fiedl nay vao db
    //tach User va UserDTO nen ko can field nay trong User

    //@Transient // field is not persistent.
    //private MultipartFile file;

    @CreatedDate // tu gen
//	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;

    @LastModifiedBy
    private Date lastUpdatedAt;

    //Cach 2 -> hay dung
    //Dung duoc khi bang user_role chi co column user va role -> them 1 column nua se ko dung cach nay duoc
    //@ElementCollection
    //@CollectionTable(name = "user_role",
    //	joinColumns = @JoinColumn(name = "user_id"))
    //@Column(name = "role")
    //private List<String> roles;

    //Cach 1
    //ko bat buoc
    //Lazy -> khi lay user se chi lay user ko lay userrole -> khi chay moi lay
    //eager -> lay user se lay luon user role
    //@JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @ManyToMany(mappedBy="users", fetch=FetchType.LAZY)
    List<Group> groups;

/*    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private Student student;*/
}
