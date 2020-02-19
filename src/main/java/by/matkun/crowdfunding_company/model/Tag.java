package by.matkun.crowdfunding_company.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "text")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToMany
    Set<Company> companies = new HashSet<>();
    public Tag() {
    }

    public Tag(String text) {
        this.text = text;
    }

}
