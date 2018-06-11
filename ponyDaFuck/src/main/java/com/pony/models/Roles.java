/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gotug
 */
@Entity
@Table
public class Roles implements Serializable {
          
     @Id
     @GeneratedValue
     private long id;

     @Column(unique = true, nullable = false)
     private String name;

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public Roles() {
          super();
     }

     public Roles(long id) {
          this.id = id;
     }

     
     public Roles(long id, String name) {
          this.id = id;
          this.name = name;
     }

     @Override
     public String toString() {
          return "Roles{" + "id=" + id + ", name=" + name + "}";
     }
         
}
