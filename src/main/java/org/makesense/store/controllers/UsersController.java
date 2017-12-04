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

@Controller
public class UsersController {
    @Autowired
    UserService service;

    @RequestMapping("/admin")
    public String getAdminPage(){
        return "/admin";
    }

    @RequestMapping("/registration")
    public String showRegistrationForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "/registration";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDTO accountDto,
             BindingResult result, WebRequest request, Errors errors) {

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("name", "message.regError");
        }
        if(result.hasErrors()) return new ModelAndView("registration", "user", accountDto);
        return new ModelAndView("successRegister", "user", accountDto);
        // rest of the implementation
    }

    private User createUserAccount(UserDTO accountDto, BindingResult result) {
        User registered = null;
        try {
            registered = service.registerNewUser(accountDto);
        } catch (UserNameExistsException e) {
            return null;
        }
        return registered;
    }
}
