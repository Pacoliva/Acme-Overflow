package services;

import domain.Evaluation;
import domain.EvaluationQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.EvaluationQuestionRepository;

import java.util.Collection;

/**
 * Created by david on 05/11/2016.
 * Copyright � 2016 NullPoint Software
 */

@Service
@Transactional
public class EvaluationQuestionService {

    // Constructors--------------------------------------------------------------------------------------

    public EvaluationQuestionService() {
        super();
    }

    // Managed repository--------------------------------------------------------------------------------

    @Autowired
    private EvaluationQuestionRepository evaluationQuestionRepository;


    // Suporting services --------------------------------------------------------------------------------

    // Simple CRUD method --------------------------------------------------------------------------------

    public EvaluationQuestion create() {
        EvaluationQuestion res = new EvaluationQuestion();
        return res;
    }

    public Collection<EvaluationQuestion> findAll() {
        Collection<EvaluationQuestion> res = evaluationQuestionRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public EvaluationQuestion findOne(int EvaluationQuestion) {
        domain.EvaluationQuestion res = evaluationQuestionRepository.findOne(EvaluationQuestion);
        Assert.notNull(res);
        return res;
    }

    public EvaluationQuestion save(EvaluationQuestion a) {
        Assert.notNull(a);
        EvaluationQuestion res = evaluationQuestionRepository.save(a);
        return res;
    }

    public void delete(EvaluationQuestion a) {
        Assert.notNull(a);
        Assert.isTrue(a.getId() != 0);
        evaluationQuestionRepository.delete(a);
    }

    // Other business methods -------------------------------------------------------------------------------

}



