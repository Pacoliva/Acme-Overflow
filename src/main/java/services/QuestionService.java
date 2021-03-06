/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package services;

import domain.Actor;
import domain.Answer;
import domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.QuestionRepository;

import java.util.*;

@Service
@Transactional
public class QuestionService {

    // Constructors--------------------------------------------------------------------------------------

    @Autowired
    private QuestionRepository questionRepository;

    // Managed repository--------------------------------------------------------------------------------
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    public QuestionService() {
        super();
    }


    // Suporting services --------------------------------------------------------------------------------

    // Simple CRUD method --------------------------------------------------------------------------------

    public Question create() {
        Question res = new Question();
        return res;
    }

    public Collection<Question> findAll() {

        Collection<Question> res = questionRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public Question findOne(int Message) {
        Question res = questionRepository.findOne(Message);
        Assert.notNull(res);
        return res;
    }

    public Question save(Question a) {
        Assert.notNull(a);
        Question res = questionRepository.save(a);
        return res;
    }

    public void delete(Question a) {
        Assert.notNull(a);
        Assert.isTrue(a.getId() != 0);
        questionRepository.delete(a);
    }

    public void flush() {
        questionRepository.flush();
    }

    // Other business methods -------------------------------------------------------------------------------
    public Boolean banQuestion(Question question) {

        Boolean res = false;
        if (question.isBanned()) {
            res = false;
        } else if (! question.isBanned()) {
            for (Answer a : question.getAnswers()) {
                a.setBanned(true);
            }
            question.setBanned(true);
            questionRepository.save(question);
            res = true;
        }
        return res;
    }


    public Boolean unbanQuestion(Question question) {
        Boolean res = false;
        if (! question.isBanned()) {
            res = true;
        } else if (question.isBanned()) {
            question.setBanned(false);
            for (Answer a : question.getAnswers()) {
                a.setBanned(false);
            }
            questionRepository.save(question);
            res = true;
        }
        return res;
    }

    public Collection<Question> notBannedQuestions() {
        Collection<Question> res = new HashSet<>();
        Collection<Question> questions = questionRepository.findAll();
        for (Question question : questions) {
            if (! question.isBanned()) {
                res.add(question);
            }
        }
        return res;
    }


    public Collection<Question> myQuestions() {
        Collection<Question> res = new HashSet<>();
        Collection<Question> all = questionRepository.findAll();
        for (Question q : all) {
            if (q.getOwner().equals(userService.findByPrincipal())) {
                res.add(q);
            }
        }
        return res;
    }


    public Collection<Answer> notBannedAnswer(Question q) {

        return questionRepository.notBannedAnswer(q);

    }

    public Collection<Answer> notBannedAnswer2(Question q) {
        Collection<Answer> res = new HashSet<>();
        Collection<Answer> questions = answerService.findAll();
        for (Answer answer : questions) {
            if (answer.getQuestion().equals(q) && ! answer.isBanned()) {
                res.add(answer);
            }
        }
        return res;
    }

    public Collection<Question> listPopular() {
        Collection<Question> questions;
        questions = notBannedQuestions();
        List<Question> res1 = new ArrayList<>();
        res1.addAll(questions);
        Collections.sort(res1, new Comparator<Question>() {
            public int compare(Question m1, Question m2) {
                return m2.getCreatedDate().toString().compareTo(m1.getCreatedDate().toString());
            }
        });
        return res1;

    }


    public int setQuestionNull(Question c) {

        return questionRepository.setQuestionNull(c);
    }


    public Collection<Answer> myAnswerOfThisQuestion(Question q, Actor actor) {

        return questionRepository.myAnswerOfThisQuestion(q, actor);
    }
}



