package com.xory.usa.Security;


import com.xory.usa.models.User;
import com.xory.usa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService; // Автоматическое внедрение

    @Autowired
    private PasswordEncoder passwordEncoder; // Автоматическое внедрение PasswordEncoder


    @GetMapping("/registrationUser")
    public String registrationUser() {
        System.out.println("заход на регистрацию");
        return "registration/registrationUser";
    }

    @PostMapping("/performRegistration")
    public String performRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      Model model) {
        System.out.println("Attempting to register user: " + username);
        if (userService.userExists(username)) {
            model.addAttribute("error", "Пользователь с таким именем уже существует.");
            return "registration/registrationUser";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Не забудьте закодировать пароль
        userService.registerUser(newUser);
        System.out.println("User registered successfully: " + newUser.getId()+" "+newUser.getUsername());
        return "redirect:/login"; // Перенаправляем на страницу логина
    }
}
