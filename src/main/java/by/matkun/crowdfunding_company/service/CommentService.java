package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Comment;
import by.matkun.crowdfunding_company.model.User;

import java.util.List;
import java.util.Map;

public interface CommentService {
    Comment find(Long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    void delete (Long id);

    void addLike(User user,Map<User,Boolean> userLike);

    boolean checkLike(User user, Comment comment);

    void addDisLike(User user,Map<User,Boolean> userLike);

    void estimate(Comment comment,User user, boolean like);
}
