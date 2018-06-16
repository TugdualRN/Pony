/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.pony.services.RoleService;

/**
 *
 * @author Gotug
 */
@Controller
@RequestMapping("/managment")
// @PreAuthorize("hasRole('ADMIN')")
public class RoleController {

     private final RoleService _roleService;

     @Autowired
     public RoleController(RoleService roleService) {
          this._roleService = roleService;
     }
    
    @GetMapping(value = {"/roles"})
    public ModelAndView listRoles() {

        List<Role> roles = _roleService.findAll();

        ModelAndView moa = new ModelAndView("managment/roles");
        moa.addObject("roles", roles);

        return moa;
    }

    @GetMapping(value = {"/role/add"})
    public ModelAndView addRole(@RequestParam String roleName) {

        if (roleName != null && !roleName.isEmpty())
        {
            Role role = new Role(roleName.toUpperCase());
            _roleService.insert(role);
        }

        return new ModelAndView("redirect:/managment/roles");
    }
    
    @GetMapping(value = {"/role/delete/{id}"})
    public ModelAndView deleteRole(@PathVariable long id) {
        
        Role role = _roleService.findById(id);
        _roleService.delete(role.getId());

        return new ModelAndView("redirect:/managment/roles");
    }
}