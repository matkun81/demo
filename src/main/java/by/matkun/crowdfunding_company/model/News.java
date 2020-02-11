package by.matkun.crowdfunding_company.model;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    private GregorianCalendar dateCreating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;


    public Long getId() {
        return id;
    }

    public News() {
        this.dateCreating = new GregorianCalendar();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GregorianCalendar getDateCreating() {
        return dateCreating;
    }
    public String getMonthCreating() {
        return String.valueOf(Month.of(dateCreating.getTime().getMonth()+1)).substring(0,3);
    }
    public String getDay() {
        return String.valueOf(DayOfWeek.of(dateCreating.getTime().getDay()));
    }

    public void setDateCreating(GregorianCalendar dateCreating) {
        this.dateCreating = dateCreating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
