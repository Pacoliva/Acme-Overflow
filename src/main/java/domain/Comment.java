/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package domain;

import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by daviddelatorre on 15/5/17.
 */
@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {


    private String title;
    private String text;
    private Date creationDate;
    private Actor owner;
    private Webinar webinar;


    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Temporal(TemporalType.DATE)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Webinar getWebinar() {
        return webinar;
    }

    public void setWebinar(Webinar webinar) {
        this.webinar = webinar;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
