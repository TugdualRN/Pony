/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.controllers;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.factories.RoleFactory;
import com.pony.forms.RoleForm;
import com.pony.models.Roles;
import com.pony.security.ConnectedUserDetails;
import com.pony.services.RoleService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gotug
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

     private final RoleService roleService;

     @Autowired
     public RoleController(RoleService roleService) {
          this.roleService = roleService;
     }

     @GetMapping(value = {"", "/"})
     public String listRoles(Model model) {

          ConnectedUserDetails connectedUserDetails = (ConnectedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          String connectedLogin = connectedUserDetails.getLogin();
          List<Roles> allRoles = roleService.findAll();
          model.addAttribute("roles", RoleFactory.toRoleDtoList(allRoles));
          return "content/role";
     }

     @GetMapping("/form")
     public ModelAndView getRoleForm(Model model) {
          return new ModelAndView("forms/role-form", "roleForm", new RoleForm());
     }

     @PostMapping("/form")
     public ModelAndView createRole(@ModelAttribute RoleForm roleForm, BindingResult bindingResult, HttpServletResponse response) {

          if (bindingResult.hasErrors()) {
               return new ModelAndView("forms/role-form", "roleForm", roleForm);
          }

          try {
               Roles role = RoleFactory.fromForm(roleForm);
               roleService.insert(role);

               return new ModelAndView("redirect:/roles");
          } catch (UniqueEntityViolationException ex) {
               roleForm.setDuplicate(true);
          }

          return new ModelAndView("forms/role-form", "roleForm", roleForm);
     }

     @GetMapping("/form/{roleId}")
     public ModelAndView getFormWithRole(@PathVariable Long roleId) throws NoSuchEntityException {
          Roles role = roleService.findById(roleId);
          return new ModelAndView("forms/role-form", "roleForm", RoleFactory.toForm(role));
     }

     @PutMapping("/form/{roleId}")
     public ModelAndView updateRole(@PathVariable Long roleId, @Valid @ModelAttribute RoleForm roleForm,
             BindingResult bindingResult, HttpServletResponse response) throws NoSuchEntityException {

          if (bindingResult.hasErrors()) {
               return new ModelAndView("forms/role-form", "roleForm", roleForm);
          }

          try {
               Roles role = RoleFactory.fromForm(roleForm);
               roleService.update(roleId, role);
               return new ModelAndView("redirect:/roles");
          } catch (UniqueEntityViolationException ex) {
               roleForm.setDuplicate(true);
          }

          return new ModelAndView("forms/role-form", "roleForm", roleForm);
     }

     @DeleteMapping("/{roleId}")
     public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
          roleService.delete(roleId);
          return new ResponseEntity<>(HttpStatus.OK);
     }
}
