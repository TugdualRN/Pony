/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.dtos;

/**
 *
 * @author Gotug
 */
public class UserDto {
     
     private Long id;
     private String login;
     private String firstname;
     private String lastname;
     private String roleName;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getLogin() {
          return login;
     }

     public void setLogin(String login) {
          this.login = login;
     }

     public String getFirstname() {
          return firstname;
     }

     public void setFirstname(String firstname) {
          this.firstname = firstname;
     }

     public String getLastname() {
          return lastname;
     }

     public void setLastname(String lastname) {
          this.lastname = lastname;
     }

     public String getRoleName() {
          return roleName;
     }

     public void setRoleName(String roleName) {
          this.roleName = roleName;
     }

     @Override
     public String toString() {
          return "UserDto{" + "id=" + id + ", login=" + login + ", firstname=" + firstname + ", lastname=" + lastname + ", roleName=" + roleName + "}";
     }
     
     
}
