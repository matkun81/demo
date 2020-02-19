package by.matkun.crowdfunding_company.model;

import lombok.Getter;
import lombok.Setter;
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
    private String title;

    @NotBlank
    @Length(max = 10000, message = "This text is too long")
    private String text;

    private GregorianCalendar dateCreating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
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
