package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.User;

import java.util.List;

public interface UserService {
    User find(Long id);

    List<User> findAll();

    User save(User user);

    void delete (Long id);

    User findByName(String name);
}
