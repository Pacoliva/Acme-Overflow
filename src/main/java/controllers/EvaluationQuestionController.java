package controllers;

import domain.Category;
import domain.Evaluation;
import domain.EvaluationQuestion;
import domain.Webinar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CategoryService;
import services.EvaluationQuestionService;
import services.EvaluationService;
import services.WebinarService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mruwzum on 19/12/16.
 */
@Controller
@RequestMapping("/evaluationQuestion")
public class EvaluationQuestionController extends AbstractController {

    @Autowired
    private EvaluationQuestionService evaluationQuestionService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private WebinarService webinarService;

    public EvaluationQuestionController() {
        super();
    }

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView result;
        List<Evaluation> evaluations = new ArrayList<>(evaluationService.findAll());
        Collection<EvaluationQuestion> aux = evaluations.get(0).getEvaluationQuestions();
        result = new ModelAndView("evaluation-question/list");
        result.addObject("categories", aux);
        result.addObject("requestURI", "evaluationQuestion/list.do");
        return result;


    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView r;

       EvaluationQuestion m = evaluationQuestionService.create();
        r = createEditModelAndView(m);
        return r;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int evaluationQuestionId) {
        ModelAndView result;
        EvaluationQuestion evaluationQuestion = evaluationQuestionService.findOne(evaluationQuestionId);
        Assert.notNull(evaluationQuestion);
        result = createEditModelAndView(evaluationQuestion);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid EvaluationQuestion evaluationQuestion, BindingResult binding) {
        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(evaluationQuestion);
        } else {
            try {
                List<Evaluation> evaluations = new ArrayList<>(evaluationService.findAll());
                evaluationQuestion.setEvaluation(evaluations.get(0));
                evaluationQuestionService.save(evaluationQuestion);
                result = this.list();

            } catch (Throwable oops) {
                result = createEditModelAndView(evaluationQuestion, "comment.commit.error");
            }
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int evaluationQuestionId) {
        ModelAndView result;
        EvaluationQuestion evaluationQuestion = evaluationQuestionService.findOne(evaluationQuestionId);


        evaluationQuestionService.delete(evaluationQuestion);
        result = new ModelAndView("redirect:list.do");

        return result;
    }



    protected ModelAndView createEditModelAndView(EvaluationQuestion evaluationQuestion) {
        ModelAndView result;

        result = createEditModelAndView(evaluationQuestion, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(EvaluationQuestion evaluationQuestion, String message) {
        ModelAndView result;
        result = new ModelAndView("evaluation-question/edit");
        result.addObject("evaluationquestion", evaluationQuestion);


        return result;


    }



    //EVALUATIONS ----------------------------------------------------------------------------------------------------



    @RequestMapping(value = "/write")
    public ModelAndView createEvaluation(@RequestParam int webinarId){

        ModelAndView res;

        Webinar webinar = webinarService.findOne(webinarId);
        List<Evaluation> evaluations = new ArrayList<>(evaluationService.findAll());
        Evaluation master = evaluations.get(0);
        Collection<EvaluationQuestion> evaluationQuestions = master.getEvaluationQuestions();

        Evaluation evaluation = evaluationService.create();
        evaluation.setEvaluationQuestions(evaluationQuestions);
        evaluation.setWebinar(webinar);

        res = new ModelAndView("evaluation-question/write");
        res.addObject("evaluation", evaluation);
        res.addObject("questions", evaluation.getEvaluationQuestions());



        return res;

    }


}
