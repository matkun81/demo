package by.matkun.crowdfunding_company.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Field
    private String title;

    @NotBlank
    @Field
    @Length(max = 10000, message = "This text is too long")
    private String text;

    private GregorianCalendar dateCreating;

    @ContainedIn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    public News() {
        this.dateCreating = new GregorianCalendar();
    }

    public GregorianCalendar getDateCreating() {
        return dateCreating;
    }
    public String getMonthCreating() {
        return String.valueOf(Month.of(dateCreating.getTime().getMonth()+1)).substring(0,3);
    }


}
