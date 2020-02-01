package by.matkun.crowdfunding_company.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String topic;

    private float avgRate;

    private float finishSum;

    private float currentSum;

    private String fileName;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bonus> listBonus;

    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> listNews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public float getFinishSum() {
        return finishSum;
    }

    public void setFinishSum(float finishSum) {
        this.finishSum = finishSum;
    }

    public float getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(float currentSum) {
        this.currentSum = currentSum;
    }

    public List<Bonus> getListBonus() {
        return listBonus;
    }

    public void setListBonus(List<Bonus> listBonus) {
        this.listBonus = listBonus;
    }

    public List<News> getListNews() {
        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews = listNews;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
