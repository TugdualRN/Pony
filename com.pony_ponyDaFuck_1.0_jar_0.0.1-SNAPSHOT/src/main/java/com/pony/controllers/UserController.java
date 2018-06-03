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

     @Autowired
     public UserController(UserService userService) {
          this._userService = userService;
     }
    
    @GetMapping(value = {"/users"})
    public String listRoles(Model model) {

         List<User> users = _userService.findAll();

         model.addAttribute("users", users);
         
         return "managment/users";
    }

    // @GetMapping(value = {"/role/add"})
    // public ModelAndView addRole(Model model, @RequestParam String roleName) {

    //     if (roleName != null && !roleName.isEmpty())
    //     {
    //         Role role = new Role(roleName.toUpperCase());
    //         try {
    //             _roleService.insert(role);
    //         } catch (Exception e) {
    //             System.out.println("Failed to insert Role " + roleName);
    //         }
    //     }

    //     return new ModelAndView("redirect:/managment/roles");
    // }

    
    // @GetMapping(value = {"/role/delete/{id}"})
    // public ModelAndView deleteRole(Model model, @PathVariable long id) {
        
    //     try {
    //         Role role = _roleService.findById(id);
    //         _roleService.delete(role.getId());

    //         return new ModelAndView("redirect:/managment/roles");
    //     } catch (Exception e) {
    //         System.out.println("Failed to Delete Role with id " + id);
    //     }

    //     return new ModelAndView("redirect:/managment/roles");
    // }
}