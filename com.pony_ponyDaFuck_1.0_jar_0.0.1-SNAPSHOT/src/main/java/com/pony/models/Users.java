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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Gotug
 */

@Entity
@Table
public class Users implements Serializable {

     @Id
     @GeneratedValue
     private long id;

     @NotBlank
     @Column(unique = true, nullable = false)
     private String login;

     @NotBlank
     @Column(nullable = false)
     private String password;

     private String firstname;

     private String lastname;

     private String phone;

     private String mail;

     @ManyToOne
     @JoinColumn(name = "role_id")
     private Roles roles;

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public String getLogin() {
          return login;
     }

     public void setLogin(String login) {
          this.login = login;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
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

     public String getPhone() {
          return phone;
     }

     public void setPhone(String phone) {
          this.phone = phone;
     }

     public String getMail() {
          return mail;
     }

     public void setMail(String mail) {
          this.mail = mail;
     }

     public Roles getRoles() {
          return roles;
     }

     public void setRoles(Roles roles) {
          this.roles = roles;
     }

     public Users() {
          super();
     }

     public Users(long id, String login, String password, String firstname, String lastname, String phone, String mail, Roles roles) {
          this.id = id;
          this.login = login;
          this.password = password;
          this.firstname = firstname;
          this.lastname = lastname;
          this.phone = phone;
          this.mail = mail;
          this.roles = roles;
     }

     @Override
     public String toString() {
          return "Users{" + "id=" + id + ", login=" + login + ", password=" + password
                  + ", firstname=" + firstname + ", lastname=" + lastname
                  + ", phone=" + phone + ", mail=" + mail + ", roles=" + roles + "}";
     }

}
