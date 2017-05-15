package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;

/**
 * Created by daviddelatorre on 15/5/17.
 */
@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {


    private String title;
    private String summary;
    private Date createdDate;
    private Category category;
    private Collection<String> pictures;
    private User owner;

    public Question() {
    }


}
