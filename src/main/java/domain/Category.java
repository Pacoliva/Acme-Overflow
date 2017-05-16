package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by daviddelatorre on 15/5/17.
 */
@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity{

    private String name;
    private String description;
    private Category father;
    private Collection<Category> sons;


    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Category getFather() {
        return father;
    }

    public void setFather(Category father) {
        this.father = father;
    }

    @OneToMany(cascade = CascadeType.PERSIST)
    public Collection<Category> getSons() {
        return sons;
    }

    public void setSons(Collection<Category> sons) {
        this.sons = sons;
    }
}
