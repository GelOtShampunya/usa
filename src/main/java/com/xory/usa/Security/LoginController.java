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
public class LoginController {

    @Autowired
    private UserService userService; // Автоматическое внедрение

    @Autowired
    private PasswordEncoder passwordEncoder; // Автоматическое внедрение PasswordEncoder

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверные учетные данные."); // Здесь вы можете настроить сообщение об ошибке
            System.out.println("отладка");
        }
        return "registration/login"; // Убедитесь, что путь к вашему шаблону логина правильный
    }

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
        System.out.println("User registered successfully: " + username);
        return "redirect:/login"; // Перенаправляем на страницу логина
    }
    @PostMapping("/performLogin")
    public String performLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        System.out.println("Okey");
        User user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Login successful for user: " + username);
            model.addAttribute("username", user.getUsername()); // Добавляем имя пользователя в модель
            return "redirect:/"; // Перенаправление на домашнюю страницу
        } else {
            System.out.println("Login failed for user: " + username);
            model.addAttribute("error", "Неверные учетные данные");
            return "registration/login";
        }
    }
}