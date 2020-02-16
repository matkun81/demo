package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService {
    User find(Long id);

    List<User> findAll();

    User save(User user);

    void delete (Long id);

    User findByName(String name);

    User findAuthorizedUser(Principal principal);

    User setRole(User user, Map<String,String> formFromFront);

    User addBonusToUser(User user, Bonus bonus);


}
