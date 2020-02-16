package by.matkun.crowdfunding_company.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Length(max = 6000,message = "Description is too long")
    private String description;

    @NotBlank
    private String topic;

    private float avgRate;

    private float finishSum;

    private float currentSum;

    private String dateOfStart;

    @NotBlank
    private String dateOfFinishing;

    private String fileName;

    private boolean isActivityTable;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bonus> listBonus;

    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    @ElementCollection(targetClass = Tag.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Company() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        this.isActivityTable = true;
        this.dateOfStart = format.format(new Date());
    }
}
