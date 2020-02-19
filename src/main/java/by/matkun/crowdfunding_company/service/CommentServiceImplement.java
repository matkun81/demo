package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.CommentRepository;
import by.matkun.crowdfunding_company.model.Comment;
import by.matkun.crowdfunding_company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public void addLike(User user, Map<User,Boolean> userLike) {
        if (userLike.containsKey(user) && userLike.get(user)){
                userLike.remove(user);
            }
           else if (userLike.containsKey(user)&&!userLike.get(user)){
                userLike.put(user,true);
            }
        else {
            userLike.put(user,true);
        }
    }
    @Override
    public void addDisLike(User user,Map<User,Boolean> userLike) {

        if (userLike.containsKey(user)&&!userLike.get(user)){
                userLike.remove(user);
            } else if (userLike.containsKey(user) && userLike.get(user)){
                userLike.put(user,false);
            }
        else {
            userLike.put(user,false);
        }

    }

    @Override
    public void estimate(Comment comment, User user, boolean like) {
        Map<User,Boolean> userLike = comment.getUsersLikes();
        if (like){
            addLike(user,userLike);
        }else {
            addDisLike(user,userLike);
        }
        comment.setUsersLikes(userLike);
        commentRepository.save(comment);
    }

    @Override
    public boolean checkLike(User user,Comment comment) {
        Map<User,Boolean> usersLikes = comment.getUsersLikes();
        if (usersLikes.containsKey(user)){
            return true;
        }
        return false;
    }


}
