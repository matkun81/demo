package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
