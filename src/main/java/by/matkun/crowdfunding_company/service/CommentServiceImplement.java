package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.CommentRepository;
import by.matkun.crowdfunding_company.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplement implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment find(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
