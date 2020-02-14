package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.*;
import by.matkun.crowdfunding_company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserServiceImplement userServiceImplement;

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private BonusServiceImplement bonusService;

    @Autowired
    private CommentServiceImplement commentService;

    @Autowired
    private NewsServiceImplement newsService;

    @GetMapping
    public String getListCompany(Principal principal, Model model){
        model.addAttribute("companyList",companyService.findAll());
      if (principal==null){
            model.addAttribute("currentUser",null);
        }else {
            User user = (User) userServiceImplement.loadUserByUsername(principal.getName());
            model.addAttribute("currentUser", user);
        }
        return "main";
    }
    @GetMapping("company/{companyId}")
    public String getCompany(Principal principal,@PathVariable (name = "companyId")Company company, Model model) throws ParseException {
        User user = (User) userServiceImplement.loadUserByUsername(principal.getName());
        model.addAttribute("currentUser",user);
        model.addAttribute("currentCompany", company);
        model.addAttribute("currentNews",company.getListNews());
        model.addAttribute("comments",company.getListComment());
        model.addAttribute("countDays",companyService.calculateRestOfDays(company.getDateOfFinishing(),company.getDateOfStart()));
        return "companyPage";
    }
    @GetMapping("content/listComment/{companyId}")
    public String getComments(Model model,@PathVariable (name = "companyId") Company company){
        model.addAttribute("comments",company.getListComment());
        return "content :: listComment";
    }

    @GetMapping("company/{companyId}/news/{newsId}")
    public String getNews(@PathVariable (name = "newsId")News news, Model model){
        model.addAttribute("currentNews",newsService.find(news.getId()));
        return "newsViewPage";
    }

    @GetMapping("company/news/{newsId}")
    public String getByDateNews(Model model,@PathVariable(name ="newsId") News news){
        model.addAttribute("listNews",newsService.findByDate(news.getDateCreating().getTime().getDay(),news.getDateCreating().getTime().getMonth()));
        return "sortByDateNews";
    }
    @PostMapping("company/{companyId}")
    public String addRating(@AuthenticationPrincipal User user,@PathVariable (name = "companyId") Long companyId,@RequestParam String avgRate, Model model){
        Company company = companyService.find(companyId);
        if (user==null){
            model.addAttribute("warningMessage","You have already set rate to this company");
        }else {
                company.setAvgRate(Float.parseFloat(companyService.calculateAvgRate(company, user, Float.parseFloat(avgRate))));
                companyService.save(company);
            }
        return "redirect:/company/{companyId}";
    }
    @PostMapping("company/{companyId}/comments")
    public String addComments(Comment comment, @PathVariable Long companyId){
        comment.setCompany(companyService.find(companyId));
        commentService.save(comment);
        return "redirect:/company/{companyId}";
    }
    @PostMapping("company/{companyId}/donate")
    public String donate(Bonus bonus, @PathVariable Long companyId,
                         @AuthenticationPrincipal User currentUser){
        User user = userServiceImplement.find(currentUser.getId());
        Bonus currentBonus = bonusService.find(bonus.getId());
        List<Bonus> listBonus = user.getBonusList();
        listBonus.add(currentBonus);
        user.setBonusList(listBonus);
        userServiceImplement.save(user);
        Company company = companyService.find(companyId);
        company.setCurrentSum(company.getCurrentSum()+currentBonus.getSumOfMoney());
        companyService.save(company);
        return "redirect:/company/{companyId}";
    }
}
