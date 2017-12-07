package org.makesense.store.controllers;

import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;
import org.makesense.store.registration.EmailExistsException;
import org.makesense.store.registration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<UserDTO> managers = service.getAllUsersByRoleList("Manager");
        model.addAttribute("managers", managers);
        return "/admin";
    }

    @RequestMapping("/registration")
    public String showRegistrationForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        model.addAttribute("registerLink", "registration");
        return "/registration";
    }

    @RequestMapping("/registerManager")
    public String showRegistrationManagerForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        model.addAttribute("registerLink", "registerManager");
        return "/registration";
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
            result.rejectValue("email", "email.exists");
        }
        if(result.hasErrors()) {
            ModelAndView modelAndView= new ModelAndView("registerManager", "user", userDTO);
            modelAndView.addObject("registerLink", "registerManager");
            return modelAndView;
        }
        return new ModelAndView("success", "user", userDTO);
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
            result.rejectValue("email", "email.exists");
        }
        if(result.hasErrors()) {
            ModelAndView modelAndView= new ModelAndView("registration", "user", userDTO);
            modelAndView.addObject("registerLink", "registration");
            return modelAndView;
        }
        return new ModelAndView("success", "user", userDTO);
    }

    private User createUserAccount(UserDTO accountDto, BindingResult result, boolean isManager) {
        User registered = null;
        try {
            registered = service.registerNewUser(accountDto, isManager);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}
