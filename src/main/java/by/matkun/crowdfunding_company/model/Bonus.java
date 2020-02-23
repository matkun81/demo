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
import java.util.List;

@Entity
@Getter
@Setter
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Field
    private String name;

    @NotBlank
    @Field
    @Length(max = 2048,message = "Description is too long")
    private String description;

    private float sumOfMoney;

    private boolean isActivity;

    @ContainedIn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> userList;

    public Bonus(){
        this.isActivity=true;
    }
}
