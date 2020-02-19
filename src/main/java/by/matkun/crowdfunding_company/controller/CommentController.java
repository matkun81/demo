package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Comment;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.service.CommentServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    private CommentServiceImplement commentService;

    @GetMapping("content/listComment/{companyId}")
    public String getComments(@PathVariable(name = "companyId") Company company, Model model) {
        model.addAttribute("comments", company.getListComment());
        return "content :: listComment";
    }
    @PostMapping("company/{companyId}/comments")
    public String addComments(Comment comment, @PathVariable(name = "companyId") Company company) {
        comment.setCompany(company);
        commentService.save(comment);
        return "redirect:/company/{companyId}";
    }
}
