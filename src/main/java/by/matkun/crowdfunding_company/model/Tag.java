package by.matkun.crowdfunding_company.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Tag() {
    }

    public Tag(String text) {
        this.text = text;
    }

}
