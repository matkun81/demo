package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Comment;

import java.util.List;

public interface CommentService {
    Comment find(Long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    void delete (Long id);
}
