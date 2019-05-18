package com.pony.controllers;

import com.pony.business.services.RoleService;
import com.pony.business.services.UserService;
import com.pony.entities.models.Role;
import com.pony.entities.models.User;
import com.pony.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
//    private final UserForm userForm;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
//        this.userForm = userForm;
    }
//    public String fillUserFormWithData(UserForm userForm, Model model) {
//
//        model.addAttribute("userForm", userForm);
//        return "forms/user-form";
//    }

//    @GetMapping("/users/{userId}/form")
//    public String userForm(@PathVariable Long userId, Model model) {
//
//        UserForm userForm = (UserForm) userService.findById(userId);
//
//        return fillUserFormWithData(userForm, model);
//
//    }

//    @GetMapping("/users/form")
//    public String userForm(Model model) {
//
//        model.addAttribute("userForm", new UserForm());
//        return "forms/user-form";
//    }


//    @PostMapping("/users/form")
//    public String createUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return fillUserFormWithData(userForm, model);
//        }
//        userService.createUser(user, user.getPasswordHash());
//        return "redirect:/users";
//    }


    @GetMapping("/users/remove/{userId}")
    public ModelAndView removeUser(@PathVariable Long userId){

        if(userId == null)
            try {
                throw new Exception("Could not find user with id- " + userId);
            } catch (Exception e) {
                e.printStackTrace();
            }

//        if(roleId == null)
//            try {
//                throw new Exception("Could not find user with id- " + userId);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//        service.deleteEmployeeById(id);
//        if (bindingResult.hasErrors()) {
//            return fillUserFormWithData(userForm, model);
//        }
        User user = userService.findById(userId);
        System.out.println(user.toString());
        Set<Role> usersRole = user.getRoles();
        System.out.println(usersRole.toString());
//        userService.removeRoleToUser(user, (Role) user.getRoles());
        Iterator itr = user.getRoles().iterator();

        while(itr.hasNext()) {
            Object element = itr.next();
            usersRole.remove(element);
            System.out.print(" ========= " + element.toString() + " DELETED successfully ! ========= ");
        }
        userService.delete(user);
        System.out.println( " ========= User data has been deleted successfully. ========= ");
        return new ModelAndView("redirect:/managment/users");

    }

}
