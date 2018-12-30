package com.shiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements Serializable {
    private static final long serialVersionUID = 5020634880144766316L;
    @Id
    private String id;
    private String role;
}
