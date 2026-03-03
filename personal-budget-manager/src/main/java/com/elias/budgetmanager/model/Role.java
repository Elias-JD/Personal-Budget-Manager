package com.elias.budgetmanager.model;

import com.elias.budgetmanager.model.enums.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    public Role() {}

    public Long getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

}
