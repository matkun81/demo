package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.UserRepository;
import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Role;
import by.matkun.crowdfunding_company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User find(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (userRepository.findByName(name) != null) {
            return userRepository.findByName(name);
        }
        if (userRepository.findByFacebookUserName(name) != null) {
            return userRepository.findByFacebookUserName(name);
        }
        if (userRepository.findByGitHubUserName(name) != null) {
            return userRepository.findByGitHubUserName(name);
        }
        return null;
    }

    @Override
    public User findAuthorizedUser(Principal principal) {
        if (principal != null) {
            return (User) loadUserByUsername(principal.getName());
        }
        return null;
    }

    @Override
    public User setRole(User user, Map<String, String> formFromFront) {
        user.setName(user.getUsername());
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : formFromFront.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        return user;
    }

    @Override
    public User addBonusToUser(User user, Bonus bonus) {
        if (user != null) {
            List<Bonus> listBonus = user.getBonusList();
            listBonus.add(bonus);
            user.setBonusList(listBonus);
            return user;
        }
        return null;
    }
}
