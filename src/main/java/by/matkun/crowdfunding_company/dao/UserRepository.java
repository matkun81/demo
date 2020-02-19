package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String s);
    boolean existsByName(String name);
    User findByFacebookUserName(String s);
    User findByGitHubUserName(String s);
}
