package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Comment;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CommentServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class CommentController {
    private final CommentServiceImplement commentService;
    private final UserServiceImplement userService;

    @GetMapping("content/listComment/{companyId}")
    public String getComments(Principal principal, @PathVariable(name = "companyId") Company company, Model model) {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("comments", company.getListComment());
        model.addAttribute("currentCompany", company);
        return "content :: listComment";
    }

    @PostMapping("company/{companyId}/comments")
    public String addComments(@Valid Comment comment, @PathVariable(name = "companyId") Company company) {
        commentService.save(comment, company);
        return "redirect:/company/{companyId}";
    }

    @PostMapping("company/{companyId}/comments/{commentId}")
    public String estimateComment(Principal principal, @PathVariable(name = "commentId") Comment comment, @RequestParam boolean like) {
        User user = userService.findAuthorizedUser(principal);
        commentService.estimate(comment, user, like);
        return "redirect:/company/{companyId}";
    }
}
