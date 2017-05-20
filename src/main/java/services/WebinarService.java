package services;

import domain.Other;
import domain.Teacher;
import domain.User;
import domain.Webinar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.WebinarRepository;

import java.util.Collection;

/**
 * Created by david on 05/11/2016.
 * Copyright � 2016 NullPoint Software
 */

@Service
@Transactional
public class WebinarService {

    // Constructors--------------------------------------------------------------------------------------

    @Autowired
    private WebinarRepository webinarRepository;

    // Managed repository--------------------------------------------------------------------------------

    public WebinarService() {
        super();
    }


    // Suporting services --------------------------------------------------------------------------------

    // Simple CRUD method --------------------------------------------------------------------------------

    public Webinar create() {
        Webinar res = new Webinar();
        return res;
    }

    public Collection<Webinar> findAll() {

        Collection<Webinar> res = webinarRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public Webinar findOne(int Message) {
        domain.Webinar res = webinarRepository.findOne(Message);
        Assert.notNull(res);
        return res;
    }

    public Webinar save(Webinar a) {
        Assert.notNull(a);
        Webinar res = webinarRepository.save(a);
        return res;
    }

    public void delete(Webinar a) {
        Assert.notNull(a);
        Assert.isTrue(a.getId() != 0);
        webinarRepository.delete(a);
    }

    // Other business methods -------------------------------------------------------------------------------


    public void flush() {
        webinarRepository.flush();
    }


    public boolean register(User user, Webinar webinar) {
        boolean res = true;
        if (webinar.getPartakers().contains(user)) {
            res = false;
        }
        user.getWebinars().add(webinar);
        webinar.getPartakers().add(user);
        return res;
    }

    public boolean unregister(User user, Webinar webinar) {
        boolean res = true;

        if (! webinar.getPartakers().contains(user)) {
            res = false;
        }
        webinar.getPartakers().remove(user);
        user.getWebinars().remove(webinar);
        return res;

    }


    public Collection<Webinar> myWebinars(Teacher t) {

        Assert.notNull(t);
        return webinarRepository.myWebinars(t);
    }

    public Collection<Webinar> myWebinarsO(Other t) {

        Assert.notNull(t);
        return webinarRepository.myWebinarso(t);
    }


    public Collection<Webinar> webinarsToGo(User u) {

        Assert.notNull(u);
        return webinarRepository.webinarsToAssist(u);

    }
}



