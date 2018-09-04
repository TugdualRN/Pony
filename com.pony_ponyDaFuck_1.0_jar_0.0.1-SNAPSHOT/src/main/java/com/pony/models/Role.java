package com.pony.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_Roles")
public class Role {
    
    // <editor-fold desc="Fields">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long id;

    @Column(unique = true, nullable = false)
    private String name;
    // </editor-fold>

    // <editor-fold desc="Constructors">
    public Role() {}
    
    public Role(String name) {
        this.name = name;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // </editor-fold>
    
    @Override
    public String toString() {
        return "Roles{" + "id=" + id + ", name=" + name + "}";
    }
}