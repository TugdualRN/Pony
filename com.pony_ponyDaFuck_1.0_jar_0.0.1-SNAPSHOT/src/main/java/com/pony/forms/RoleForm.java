/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.forms;

import javax.validation.constraints.Pattern;

/**
 *
 * @author Gotug
 */
public class RoleForm {
     // Usefull to define if POST or PUT method

     private Long id;

     private String name;

     private boolean duplicate = false;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public boolean isDuplicate() {
          return duplicate;
     }

     public void setDuplicate(boolean duplicate) {
          this.duplicate = duplicate;
     }

     public RoleForm() {
          super();
     }

     public RoleForm(Long id, String name) {
          this.id = id;
          this.name = name;
     }

     @Override
     public String toString() {
          return "RoleForm{" + "id=" + id + ", name=" + name + ", duplicate=" + duplicate + "}";
     }

}
