package by.matkun.crowdfunding_company.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private String description;

    private double sumOfMoney;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> userList;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSumOfMoney() {
        return sumOfMoney;
    }

    public void setSumOfMoney(double sumOfMoney) {
        this.sumOfMoney = sumOfMoney;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
