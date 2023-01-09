package com.java.accountService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private String username;
}
