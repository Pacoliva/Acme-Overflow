/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package services;

import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.CommentRepository;

import java.util.Collection;

@Service
@Transactional
public class CommentService {

    // Constructors--------------------------------------------------------------------------------------

    @Autowired
    private CommentRepository CommentRepository;

    // Managed repository--------------------------------------------------------------------------------

    public CommentService() {
        super();
    }


    // Suporting services --------------------------------------------------------------------------------

    // Simple CRUD method --------------------------------------------------------------------------------

    public Comment create() {

        Comment res = new Comment();
        return res;
    }

    public Collection<Comment> findAll() {
        Collection<Comment> res = CommentRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public Comment findOne(int Comment) {
        domain.Comment res = CommentRepository.findOne(Comment);
        Assert.notNull(res);
        return res;
    }

    public Comment save(Comment a) {
        Assert.notNull(a);
        Comment res = CommentRepository.save(a);
        return res;
    }

    public void delete(Comment a) {
        Assert.notNull(a);
        Assert.isTrue(a.getId() != 0);
        CommentRepository.delete(a);
    }

    // Other business methods -------------------------------------------------------------------------------

}



