/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.factories;

import com.pony.dtos.RoleDto;
import com.pony.forms.RoleForm;
import com.pony.models.Roles;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gotug
 */
public class RoleFactory {

     // Sent to the front-end
     public static RoleDto toRoleDto(Roles role) {

          if (role == null) {
               return null;
          }

          RoleDto dto = new RoleDto();

          dto.setId(role.getId());
          dto.setName(role.getName());

          return dto;
     }

     // Sent to the front-end
     public static List<RoleDto> toRoleDtoList(List<Roles> roles) {

          List<RoleDto> dtos = new ArrayList();

          if (roles == null) {
               return dtos;
          }

          roles.forEach((oneRole) -> {
               dtos.add(toRoleDto(oneRole));
          });

          return dtos;
     }

     // Convert entity to form
     public static RoleForm toForm(Roles role) {

          if (role == null) {
               return null;
          }

          RoleForm form = new RoleForm();

          form.setId(role.getId());
          form.setName(role.getName());

          return form;
     }

     // Convert form to entity
     public static Roles fromForm(RoleForm roleForm) {

          if (roleForm == null) {
               return null;
          }

          Roles role = new Roles();

          if (roleForm.getId() == null) {
               role.setId(role.getId());
               role.setName(roleForm.getName());
          } else {
               role.setId(roleForm.getId());
               role.setName(roleForm.getName());
          }

          return role;
     }
}
