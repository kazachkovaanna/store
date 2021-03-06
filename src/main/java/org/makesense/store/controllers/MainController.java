package org.makesense.store.controllers;

import org.makesense.store.cart.Cart;
import org.makesense.store.cart.CartDTO;
import org.makesense.store.editing.ProductNameExistsException;
import org.makesense.store.editing.ProductNameNotExistsException;
import org.makesense.store.editing.ProductService;
import org.makesense.store.models.*;
import org.makesense.store.repository.OrdersRepository;
import org.makesense.store.repository.ProductsRepository;
import org.makesense.store.repository.RolesRepository;
import org.makesense.store.repository.UsersRepository;
import org.makesense.store.storage.ImagesStorage;
import org.makesense.store.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ImagesStorage storageService;
    @Autowired
    ServletContext servletContext;



    @RequestMapping("/")
    public String mainPage(Model model){
        List<Product> products = repository.findAll();
        model.addAttribute("title", "Список товаров");
        model.addAttribute("header", "Список товаров");
        model.addAttribute("products", products);
        model.addAttribute("text", "Наш магазин рад предложит вам следующие товары:");
        return "mainPage";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
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

    @RequestMapping("/cartContent")
    public String getCartContent(Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart != null) model.addAttribute("products", cart.getProducts());
        model.addAttribute("title", "Корзина");
        model.addAttribute("header", "Список покупок");
        model.addAttribute("text", "Вы собираетесь купить:");
        model.addAttribute("cart", "true");
        return "mainPage";
    }


    @RequestMapping("/productEdit")
    public String editProduct(Model model, @RequestParam(required = false) String name){
        Product product = repository.findByName(name);
        ProductDTO dto;
        if(product == null) dto = new ProductDTO();
        else dto = new ProductDTO(product);
        model.addAttribute("product", dto);
        return "productEdit";
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public ModelAndView handleSaveProduct(@RequestParam("file") MultipartFile file, @ModelAttribute("product") @Valid ProductDTO productDTO,
                                          BindingResult result, MultipartHttpServletRequest request, Errors errors, @RequestParam String newProduct) {
        Product saved = new Product();
        if (!result.hasErrors()) {
            try {
                if(file!= null) storageService.store(file, productDTO);
                saved = saveProduct(productDTO, result, newProduct.equals("true"));
            } catch (ProductNameExistsException e) {
                result.rejectValue("name", "name.occupied");
            } catch (ProductNameNotExistsException e) {
                result.rejectValue("name", "name.not.exists");;
            } catch (IOException e) {
                result.rejectValue("image", "image.error");
            }
        }
        if (saved == null) {
            result.rejectValue("name", "Имя занято");
        }
        if(result.hasErrors()) return new ModelAndView("productEdit", "product", productDTO);
        return new ModelAndView("success", "product", productDTO);
    }

    private Product saveProduct(ProductDTO product, BindingResult result, boolean isNew) throws ProductNameExistsException, ProductNameNotExistsException {
        if( isNew) return productService.registerNewProduct(product);
        else return  productService.updateProduct(product);
    }

    @RequestMapping("/manage")
    public String manage( Model model){
        List<Product> products = repository.findAll();
        model.addAttribute("title", "Список товаров");
        model.addAttribute("header", "Список товаров");
        model.addAttribute("products", products);
        return "mainPage";
    }

    @RequestMapping("/order")
    public String order(HttpSession session, Authentication auth){
        Cart cart = (Cart) session.getAttribute("cart");
        String user = auth.getName();
        if(cart!= null && user != null & !user.equals("")){
            Order order = new Order(user, cart.getProducts());
            ordersRepository.save(order);
            session.removeAttribute("cart");
            session.invalidate();
        }
        return "redirect:/";
    }

}
