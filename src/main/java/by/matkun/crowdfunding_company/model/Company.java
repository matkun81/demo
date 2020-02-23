package by.matkun.crowdfunding_company.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
@Indexed
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Field
    private String name;

    @Field
    private String topic;
    @NotBlank
    @Field
    @Length(max = 6000,message = "Description is too long")
    private String description;

    private float finishSum;

    private String dateOfStart;

    @NotBlank
    private String dateOfFinishing;

    private String fileName;

    private boolean isActivityTable;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_users_donates",
            joinColumns = {@JoinColumn(name = "company_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "donate_name")
    @Column(name = "donate")
    private Map<User,Float> usersDonates;

    @ElementCollection(targetClass = Topic.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "company_topic", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    private Set<Topic> topics;

    @IndexedEmbedded
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bonus> listBonus;

    @IndexedEmbedded
    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> listNews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_item_mapping",
            joinColumns = {@JoinColumn(name = "company_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_name")
    @Column(name = "rate")
    private Map<User,Float> usersRate;

    @OneToMany (mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> listComment;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    public Company() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        this.isActivityTable = true;
        this.dateOfStart = format.format(new Date());
    }

    public float getCurrentSum(){
        float sum = 0;
        for (Map.Entry<User, Float> item : getUsersDonates().entrySet()) {
            sum += item.getValue();
        }
        return sum;
    }
    public String getAvgRate(){
        float sum = 0;
        for (Map.Entry<User, Float> item :getUsersRate().entrySet()) {
            sum += item.getValue();
        }
        return String.valueOf(sum / getUsersRate().size());
    }
}
