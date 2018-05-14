/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.forms;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Gotug
 */
public class UserForm {
     
     // Usefull to define if POST or PUT method
    private Long id;
    
    @NotBlank
    private String login;

    @NotBlank
    private String password;
    
    @NotBlank
    private String passwordConfirm;

    private String firstname;

    private String lastname;

    private String phone;

    private String mail;
    
    @Range(min = 1L) // Select a valid role is mandatory
    private Long roleId = 0L;

    private boolean duplicate = false;
    private boolean confirmationOk = true;

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

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getPasswordConfirm() {
          return passwordConfirm;
     }

     public void setPasswordConfirm(String passwordConfirm) {
          this.passwordConfirm = passwordConfirm;
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

     public Long getRoleId() {
          return roleId;
     }

     public void setRoleId(Long roleId) {
          this.roleId = roleId;
     }

     public boolean isDuplicate() {
          return duplicate;
     }

     public void setDuplicate(boolean duplicate) {
          this.duplicate = duplicate;
     }

     public boolean isConfirmationOk() {
          return confirmationOk;
     }

     public void setConfirmationOk(boolean confirmationOk) {
          this.confirmationOk = confirmationOk;
     }

     @Override
     public String toString() {
          return "UserForm{" + "id=" + id + ", login=" + login + ", password=" + password + ", passwordConfirm=" + passwordConfirm + ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", mail=" + mail + ", roleId=" + roleId + ", duplicate=" + duplicate + ", confirmationOk=" + confirmationOk + "}";
     }
    
    
}
