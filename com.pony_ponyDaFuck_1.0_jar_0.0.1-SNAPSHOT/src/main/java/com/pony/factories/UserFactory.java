/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.factories;

import com.pony.dtos.UserDto;
import com.pony.forms.UserForm;
import com.pony.models.Role;
import com.pony.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gotug
 */
public class UserFactory {

     private static final String DEFAULT_PASSWORD = "password_hidden";

     // Sent to the front-end
     public static UserDto toUserDto(User user) {

        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setLogin(user.getUserName());
        dto.setLastname(user.getLastName());
        dto.setFirstname(user.getFirstName());

        List<Role> roles = user.getRoles();
        if (roles != null) {
            dto.setRoleName(roles.get(0).getName());
        }

        return dto;
     }

     // Sent to the front-end
     public static List<UserDto> toUserDtoList(List<User> users) {

        List<UserDto> dtos = new ArrayList<>();

        if (users == null) {
            return dtos;
        }

        users.forEach((oneUser) -> {
            dtos.add(toUserDto(oneUser));
        });

        return dtos;
     }

     // entity to form
     public static UserForm toForm(User user) {

        if (user == null) {
            return null;
        }

        UserForm form = new UserForm();

        form.setId(user.getId());
        form.setLogin(user.getUserName());
        form.setLastname(user.getLastName());
        form.setFirstname(user.getFirstName());
        form.setPhone(user.getPhone());
        form.setMail(user.getMail());

        // We don't want to show the current password in the form
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        List<Role> userRoles = user.getRoles();
        if (userRoles != null) {
            form.setRoleId(userRoles.get(0).getId());
        }

        return form;
     }

     // form to entity
     public static User fromForm(UserForm userForm) {

        if (userForm == null) {
            return null;
        }

        User user = new User();

        if (userForm.getId() == null) {

            // user.setId(userForm.getId());
            user.setId(user.getId());
            user.setUserName(userForm.getLogin());
            user.setLastName(userForm.getLastname());
            user.setFirstName(userForm.getFirstname());
            user.setPhone(userForm.getPhone());
            user.setMail(userForm.getMail());

            // If the password equals DEFAULT_PASSWORD; the password has not been changed
            if (!DEFAULT_PASSWORD.equals(userForm.getPassword())) {
                user.setPasswordHash(userForm.getPassword());
            }

            Long roleId = userForm.getRoleId();
            if (roleId != null && !roleId.equals(0L)) { // If a role has been selected
                List<Role> roles = new ArrayList<Role>();
                roles.add(new Role(roleId));
                user.setRoles(roles);
            }

        } else {

            user.setId(userForm.getId());
            user.setUserName(userForm.getLogin());
            user.setLastName(userForm.getLastname());
            user.setFirstName(userForm.getFirstname());
            user.setPhone(userForm.getPhone());
            user.setMail(userForm.getMail());

            // If the password equals DEFAULT_PASSWORD; the password has not been changed
            if (!DEFAULT_PASSWORD.equals(userForm.getPassword())) {
                user.setPasswordHash(userForm.getPassword());
            }

            Long roleId = userForm.getRoleId();
            if (roleId != null && !roleId.equals(0L)) { // If a role has been selected
                List<Role> roles = new ArrayList<Role>();
                roles.add(new Role(roleId));
                user.setRoles(roles);
            }

        }

        return user;
    }
}