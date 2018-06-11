package com.pony.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String listUsers(Model model) {

         List<User> users = _userService.findAll();
         List<Role> roles = _roleService.findAll();

         model.addAttribute("users", users);
         model.addAttribute("roles", roles);
         
         return "managment/users";
    }

    @GetMapping(value = {"/user/addrole"})
    public String addUserToRole(Model model, @RequestParam long userId, @RequestParam long roleId)
    {
        try {
            User user = _userService.findById(userId);
            List<Role> userRoles = user.getRoles();
            Role role = _roleService.findById(roleId);

            // Not working, need fix
            if (userRoles.stream().filter((Role) -> role.getId() == roleId).count() == 0) {
                if (userRoles.add(role)) {
                    _userService.update(user.getId(), user);
                }
            }
        }
        catch (Exception e) {

        }

        return "managment/users";
    }

    public String removeUserFromRole(Model model, @RequestParam long userId, @RequestParam long roleId)
    {
        return "managment/users";
    }
}