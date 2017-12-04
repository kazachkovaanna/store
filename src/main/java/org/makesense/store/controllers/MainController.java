package org.makesense.store.controllers;

import org.makesense.store.cart.Cart;
import org.makesense.store.cart.CartDTO;
import org.makesense.store.models.Product;
import org.makesense.store.models.Role;
import org.makesense.store.models.User;
import org.makesense.store.repository.ProductsRepository;
import org.makesense.store.repository.RolesRepository;
import org.makesense.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ProductsRepository repository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @RequestMapping("/")
    public String mainPage(Model model){
//        Product product1 = new Product(null, "Product 1", "sdescr1", "fdescr1", null, 123.02, 0, null, "Test", null, 12);
//        Product product2 = new Product(null, "Product 2", "sdescr2", "fdescr2", null, 148.02, 0, null, "Test", null, 3);
//        repository.save(product1);
//        repository.save(product2);
//        Role [] roles = new Role[1];
//        roles[0] = new Role("User");
//        roles[0] = rolesRepository.save(roles[0]);
//        User a = new User("anna", "123", new HashSet<Role>(Arrays.asList(roles)));
//        usersRepository.save(a);
        List<Product> products = repository.findAll();
        model.addAttribute("products", products);
        return "mainPage";
    }

    @RequestMapping(value = "/product*", method = RequestMethod.GET)
    public String product(Model model, @RequestParam String name){
        Product product = repository.findByName(name);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartDTO addToCart(HttpSession session, @RequestParam String name){
        Product product = repository.findByName(name);
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            cart.add(product);
            session.setAttribute("cart", cart);
        }
        else{
            cart.add(product);
        }
        CartDTO dto = new CartDTO(cart.size(),cart.summ());
        return dto;
        //return "{\"cart\":\""+cart.summ()+"\"}";
    }

    @RequestMapping("/checkout")
    public String checkout(){
        return "checkout";
    }
}
