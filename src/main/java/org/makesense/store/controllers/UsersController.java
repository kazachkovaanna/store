package org.makesense.store.controllers;

import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;
import org.makesense.store.registration.UserNameExistsException;
import org.makesense.store.registration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    UserService service;

    @RequestMapping("/admin")
    public String getAdminPage(Model model){
        List<String> managers = service.getAllUsersByRoleList("Manager");
        model.addAttribute("managers", managers);
        return "/admin";
    }

    @RequestMapping("/registration")
    public String showRegistrationForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "/registration";
    }

    @RequestMapping("/registerManager")
    public String showRegistrationManagerForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "/registerManager";
    }

    @RequestMapping(value = "/registerManager", method = RequestMethod.POST)
    public ModelAndView registerManagerAccount
            (@ModelAttribute("user") @Valid UserDTO userDTO,
             BindingResult result, WebRequest request, Errors errors){
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(userDTO, result, true);
        }
        if (registered == null) {
            result.rejectValue("name", "Имя занято");
        }
        if(result.hasErrors()) {
            ModelAndView modelAndView= new ModelAndView("registerManager", "user", userDTO);
            //modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        return new ModelAndView("successRegister", "user", userDTO);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDTO userDTO,
             BindingResult result, WebRequest request, Errors errors) {

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(userDTO, result, false);
        }
        if (registered == null) {
            result.rejectValue("name", "Имя занято");
        }
        if(result.hasErrors()) return new ModelAndView("registration", "user", userDTO);
        return new ModelAndView("successRegister", "user", userDTO);
    }

    private User createUserAccount(UserDTO accountDto, BindingResult result, boolean isManager) {
        User registered = null;
        try {
            registered = service.registerNewUser(accountDto, isManager);
        } catch (UserNameExistsException e) {
            return null;
        }
        return registered;
    }
}
