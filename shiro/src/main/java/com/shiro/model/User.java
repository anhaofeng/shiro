package com.shiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 5020634880144766316L;
    @Id
    private String id;
    private String userName;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private Set<Role> roles;
}
