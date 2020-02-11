package by.matkun.crowdfunding_company.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String topic;

    private String tag;

    private float avgRate;

    private float finishSum;

    private float currentSum;

    private String dateOfStart;

    private String dateOfFinishing;

    private String fileName;

    private boolean isActivityTable;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bonus> listBonus;

    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<News> listNews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "company_item_mapping",
            joinColumns = {@JoinColumn(name = "company_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_name")
    @Column(name = "rate")
    private Map<User,Float> usersRate;

    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> listComment;

    public Company() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        this.isActivityTable = true;
        this.dateOfStart = format.format(new Date());
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public boolean isActivityTable() {
        return isActivityTable;
    }

    public void setActivitiTable(boolean activitiTable) {
        isActivityTable = activitiTable;
    }

    public Map<User, Float> getUsersRate() {
        return usersRate;
    }

    public void setUsersRate(Map<User, Float> usersRate) {
        this.usersRate = usersRate;
    }

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getDateOfFinishing() {
        return dateOfFinishing;
    }

    public void setDateOfFinishing(String dateOfFinishing) {
        this.dateOfFinishing = dateOfFinishing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
