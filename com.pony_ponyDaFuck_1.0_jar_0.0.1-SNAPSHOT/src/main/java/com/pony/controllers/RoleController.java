package com.pony.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pony.entities.models.Role;
import com.pony.business.services.RoleService;

@Controller
@RequestMapping("/managment")
//@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

     private final RoleService _roleService;

     @Autowired
     public RoleController(RoleService roleService) {
          this._roleService = roleService;
     }
    
    @GetMapping(value = {"/roles"})
    public ModelAndView listRoles() {

        List<Role> roles = _roleService.findAll();

        return new ModelAndView("managment/roles")
            .addObject("roles", roles);
    }

    @GetMapping(value = {"/role/add"})
    public ModelAndView addRole(@RequestParam String roleName) {

        if (roleName != null && !roleName.isEmpty()) {
            _roleService.addRole(new Role(roleName.toUpperCase()));
        }

        return new ModelAndView("redirect:/managment/roles");
    }
    
    @GetMapping(value = {"/role/delete/{id}"})
    public ModelAndView deleteRole(@PathVariable long id) {
        
        Role role = _roleService.findById(id).orElseGet(null);;
        _roleService.deleteRole(role);

        return new ModelAndView("redirect:/managment/roles");
    }
}