package com.xory.usa.controllers;

import com.xory.usa.models.News;
import com.xory.usa.services.NewsService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String index(Model model) {
        // Получаем список новостей
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);

        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Гость";

        // Передаем имя пользователя в модель
        model.addAttribute("username", username);

        return "index"; // Возвращает имя шаблона
    }
    @GetMapping("/loginUser")
    public String regAndLogPage(){
        return "registration/loginUser";
    }
    @GetMapping("/awards")
    public String awards() {
        return "army/rewards"; // Возвращает имя шаблона rewards.html
    }
    @GetMapping("/positions")
    public String positions() {
        return "army/positions"; // Возвращаеет имя шаблона rewards.html
    }
}

