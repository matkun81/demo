package by.matkun.crowdfunding_company.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String text;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "comment_usr_mapping",
            joinColumns = {@JoinColumn(name = "comment_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "usr")
    @Column(name = "likes")
    private Map<User,Boolean> usersLikes = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Company company;

    public boolean checkLike(User user) {
        if (usersLikes.containsKey(user)&& usersLikes.get(user)){
            return true;
        }
        return false;
    }
    public boolean checkDisLike(User user) {
        if (usersLikes.containsKey(user)&& !usersLikes.get(user)){
            return true;
        }
        return false;
    }

    public long countLike(){
        int count=0;
        for (Map.Entry<User, Boolean> item :usersLikes.entrySet()) {
            if (item.getValue()){
                count++;
            }
        }
       return count;
    }

    public long countDisLike(){
        int count=0;
        for (Map.Entry<User, Boolean> item :usersLikes.entrySet()) {
            if (!item.getValue()){
                count++;
            }
        }
        return count;
    }
}
