/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.factories;

import com.pony.dtos.UserDto;
import com.pony.forms.UserForm;
import com.pony.models.Roles;
import com.pony.models.Users;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gotug
 */
public class UserFactory {

     private static final String DEFAULT_PASSWORD = "password_hidden";

     // Sent to the front-end
     public static UserDto toUserDto(Users user) {

          if (user == null) {
               return null;
          }

          UserDto dto = new UserDto();

          dto.setId(user.getId());
          dto.setLogin(user.getLogin());
          dto.setLastname(user.getLastname());
          dto.setFirstname(user.getFirstname());

          Roles userRole = user.getRoles();
          if (userRole != null) {
               dto.setRoleName(userRole.getName());
          }

          return dto;
     }

     // Sent to the front-end
     public static List<UserDto> toUserDtoList(List<Users> users) {

          List<UserDto> dtos = new ArrayList();

          if (users == null) {
               return dtos;
          }

          users.forEach((oneUser) -> {
               dtos.add(toUserDto(oneUser));
          });

          return dtos;
     }

     // entity to form
     public static UserForm toForm(Users user) {

          if (user == null) {
               return null;
          }

          UserForm form = new UserForm();

          form.setId(user.getId());
          form.setLogin(user.getLogin());
          form.setLastname(user.getLastname());
          form.setFirstname(user.getFirstname());
          form.setPhone(user.getPhone());
          form.setMail(user.getMail());

          // We don't want to show the current password in the form
          form.setPassword(DEFAULT_PASSWORD);
          form.setPasswordConfirm(DEFAULT_PASSWORD);

          Roles userRole = user.getRoles();
          if (userRole != null) {
               form.setRoleId(userRole.getId());
          }

          return form;
     }

     // form to entity
     public static Users fromForm(UserForm userForm) {

          if (userForm == null) {
               return null;
          }

          Users user = new Users();

          if (userForm.getId() == null) {

//               user.setId(userForm.getId());
               user.setId(user.getId());
               user.setLogin(userForm.getLogin());
               user.setLastname(userForm.getLastname());
               user.setFirstname(userForm.getFirstname());
               user.setPhone(userForm.getPhone());
               user.setMail(userForm.getMail());

               // If the password equals DEFAULT_PASSWORD; the password has not been changed
               if (!DEFAULT_PASSWORD.equals(userForm.getPassword())) {
                    user.setPassword(userForm.getPassword());
               }

               Long roleId = userForm.getRoleId();
               if (roleId != null && !roleId.equals(0L)) { // If a role has been selected
                    user.setRoles(new Roles(roleId));
               }

          } else {

               user.setId(userForm.getId());
               user.setLogin(userForm.getLogin());
               user.setLastname(userForm.getLastname());
               user.setFirstname(userForm.getFirstname());
               user.setPhone(userForm.getPhone());
               user.setMail(userForm.getMail());

               // If the password equals DEFAULT_PASSWORD; the password has not been changed
               if (!DEFAULT_PASSWORD.equals(userForm.getPassword())) {
                    user.setPassword(userForm.getPassword());
               }

               Long roleId = userForm.getRoleId();
               if (roleId != null && !roleId.equals(0L)) { // If a role has been selected
                    user.setRoles(new Roles(roleId));
               }

          }

          return user;
     }
}
