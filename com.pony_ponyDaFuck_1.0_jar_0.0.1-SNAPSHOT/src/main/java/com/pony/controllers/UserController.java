package com.pony.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pony.models.Role;
import com.pony.models.User;
import com.pony.services.RoleService;
import com.pony.services.UserService;

@Controller
@RequestMapping("/managment")
// @PreAuthorize("hasRole('ADMIN')")
public class UserController {

     private final UserService _userService;
     private final RoleService _roleService;

     @Autowired
     public UserController(UserService userService, RoleService roleService) {
          this._userService = userService;
          this._roleService = roleService;
     }
    
    @GetMapping(value = {"/users"})
    public ModelAndView listUsers() {

        List<User> users = _userService.findAll();
        List<Role> roles = _roleService.findAll();

        return new ModelAndView("/managment/users")
            .addObject("users", users)
            .addObject("roles", roles);
    }

    @GetMapping(value = {"/user/addrole"})
    public ModelAndView addUserToRole(@RequestParam long userId, @RequestParam long roleId)
    {
        User user = _userService.findById(userId);
        Set<Role> userRoles = user.getRoles();
        Role role = _roleService.findById(roleId);

        if (!_userService.hasRole(user, role)) {
            if (userRoles.add(role)) {
                _userService.update(user);
            }
        }

        return new ModelAndView("redirect:/managment/users");
    }

    @GetMapping(value = {"/user/removerole/{userId}/{roleId}"})
    public ModelAndView removeUserFromRole(@PathVariable long userId, @PathVariable long roleId)
    {
        User user = _userService.findById(userId);
        Set<Role> userRoles = user.getRoles();
        Role role = _roleService.findById(roleId);

        if (userRoles.removeIf(x -> x.getId() == role.getId())) {
            _userService.update(user);
        }

        return new ModelAndView("redirect:/managment/users");
    }
}