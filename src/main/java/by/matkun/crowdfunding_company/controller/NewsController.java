package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.News;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.NewsServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/{userId}/company/{companyId}/news")
public class NewsController {

    @Autowired
    private NewsServiceImplement newsService;

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private UserServiceImplement userService;
    @GetMapping
    public String getNews(Principal principal,@PathVariable (name = "companyId") Company company, @PathVariable Long userId, Model model){
        List<News> newsList = company.getListNews();
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("listNews", newsList);
        model.addAttribute("companyId",company.getId());
        model.addAttribute("userId",userId);
        return "newsPage";
    }
    @PostMapping
    public String createNews(@PathVariable (name = "companyId") Company company, News news){
        news.setCompany(company);
        newsService.save(news);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }

    @GetMapping("/editNews/{newsId}")
    public String editNews(Principal principal,@PathVariable Long newsId,@PathVariable Long userId,@PathVariable Long companyId, Model model){
        News currentNews = newsService.find(newsId);
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("currentNews", currentNews);
        model.addAttribute("userId", userId);
        model.addAttribute("companyId",companyId);
        return "editNews";
    }

    @PostMapping("/editNews/{newsId}")
    public String updateNews(News news, @PathVariable Long newsId){
        news.setId(newsId);
        newsService.save(news);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }
    @PostMapping("/deleteNews")
    public String deleteNews(@RequestParam Long newsId){
        newsService.delete(newsId);
        return "redirect:/user/{userId}/company/{companyId}/news";
    }
}
