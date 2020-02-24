package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.News;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.NewsServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/company/{companyId}/news")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class NewsController {

    private final NewsServiceImplement newsService;
    private final UserServiceImplement userService;

    @GetMapping
    public String getNews(Principal principal, @PathVariable(name = "companyId") Company company, @PathVariable Long userId, Model model) {
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("listNews", company.getListNews());
        model.addAttribute("companyId", company.getId());
        model.addAttribute("userId", userId);
        return "newsPage";
    }

    @PostMapping
    public String createNews(@PathVariable(name = "companyId") Company company, News news) {
        newsService.create(news, company);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }

    @GetMapping("/editNews/{newsId}")
    public String editNews(Principal principal, @PathVariable Long newsId, @PathVariable Long userId, @PathVariable Long companyId, Model model) {
        News currentNews = newsService.find(newsId);
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentNews", currentNews);
        model.addAttribute("userId", userId);
        model.addAttribute("companyId", companyId);
        return "editNews";
    }

    @PostMapping("/editNews/{newsId}")
    public String updateNews(News news, @PathVariable Long newsId) {
        newsService.update(news, newsId);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }

    @PostMapping("/deleteNews")
    public String deleteNews(@RequestParam Long newsId) {
        newsService.delete(newsId);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }
}
