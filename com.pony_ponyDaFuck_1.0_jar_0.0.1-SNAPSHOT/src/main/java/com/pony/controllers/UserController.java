/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.controllers;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.factories.RoleFactory;
import com.pony.factories.UserFactory;
import com.pony.forms.UserForm;
import com.pony.models.Users;
import com.pony.services.RoleService;
import com.pony.services.UserService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
public class UserController {

     private final UserService userService;
     private final RoleService roleService;

     @Autowired
     public UserController(UserService userService, RoleService roleService) {
          this.userService = userService;
          this.roleService = roleService;
     }

     @GetMapping(value = {"", "/"})
     public String listUser(Model model) {

          List<Users> allUsers = userService.findAll();
          model.addAttribute("users", UserFactory.toUserDtoList(allUsers));
          return "content/user";
     }

     private ModelAndView userFormData(UserForm userForm) {
          ModelAndView modelAndView = new ModelAndView("forms/user-form", "userForm", userForm);
          modelAndView.addObject("roles", RoleFactory.toRoleDtoList(roleService.findAll()));
          return modelAndView;
     }

     @GetMapping("/form")
     public ModelAndView getUserForm(Model model) {
          return userFormData(new UserForm());
     }

     @PostMapping("/form")
     public ModelAndView createUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpServletResponse response) {

          String password = userForm.getPassword();
          String confirm = userForm.getPasswordConfirm();

          if (password != null && confirm != null && !password.equals(confirm)) {
               userForm.setConfirmationOk(false);
          }

          if (bindingResult.hasErrors() || !userForm.isConfirmationOk()) {
               return userFormData(userForm);
          }

          try {
               Users user = UserFactory.fromForm(userForm);
               userService.insert(user);
               return new ModelAndView("redirect:/users");
          } catch (UniqueEntityViolationException ex) {
               userForm.setDuplicate(true);
          }

          return userFormData(userForm);
     }

     @GetMapping("/form/{userId}")
     public ModelAndView getFormWithUser(@PathVariable Long userId) throws NoSuchEntityException {
          Users user = userService.findById(userId);
          return userFormData(UserFactory.toForm(user));
     }

     @PutMapping("/form/{userId}")
     public ModelAndView updateUser(@PathVariable Long userId, @Valid @ModelAttribute UserForm userForm,
             BindingResult bindingResult, HttpServletResponse response) throws NoSuchEntityException {

          String password = userForm.getPassword();
          String confirm = userForm.getPasswordConfirm();

          // If the password and it's confirmation does not match
          if (password != null && confirm != null && !password.equals(confirm)) {
               userForm.setConfirmationOk(false);
          }

          if (bindingResult.hasErrors() || !userForm.isConfirmationOk()) {
               return userFormData(userForm);
          }

          try {
               Users user = UserFactory.fromForm(userForm);
               userService.update(userId, user);
               return new ModelAndView("redirect:/users");
          } catch (UniqueEntityViolationException ex) {
               userForm.setDuplicate(true);
          }

          return userFormData(userForm);
     }

     @DeleteMapping("/{userId}")
     public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
          userService.delete(userId);
          return new ResponseEntity<>(HttpStatus.OK);
     }
}
