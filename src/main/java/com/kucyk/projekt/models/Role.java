package com.kucyk.projekt.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role implements GrantedAuthority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)//przechowywane w postaci łańcucha znaków
    private Types type;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)//właściciel relacji to roles
    private Set<User> users;


    public Role(Types type){
        this.type = type;
    }

    @Override
    public String getAuthority()
    {
        return type.toString();
    }

    public static enum Types
    {
        ROLE_ADMIN,
        ROLE_USER
    }
}
