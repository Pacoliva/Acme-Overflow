/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package controllers;


import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.UserAccountService;
import services.CreditCardService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    //Services ----------------------------------------------------------------

    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UserAccountService userAccountService;

    //Constructors----------------------------------------------

    public UserController() {
        super();
    }

    protected static ModelAndView createEditModelAndView(User user) {
        ModelAndView result;

        result = createEditModelAndView(user, null);

        return result;
    }


    //Create Method -----------------------------------------------------------

    protected static ModelAndView createEditModelAndView(User user, String message) {
        ModelAndView result;

        result = new ModelAndView("user/edit");
        result.addObject("user", user);
        result.addObject("message", message);

        return result;

    }

    protected static ModelAndView createEditModelAndView2(User user) {
        ModelAndView result;

        result = createEditModelAndView2(user, null);

        return result;
    }
    // Edition ---------------------------------------------------------

    protected static ModelAndView createEditModelAndView2(User user, String message) {
        ModelAndView result;

        result = new ModelAndView("user/register");
        result.addObject("user", user);
        result.addObject("message", message);

        return result;

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView userList() {

        ModelAndView result;
        Collection<User> users;

        Collection<User> res = new HashSet<>();
        users = userService.findAll();


        res.addAll(userService.userNotBanned());


        result = new ModelAndView("user/list");
        result.addObject("users", res);
        result.addObject("requestURI", "user/list.do");

        return result;
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView userListAll() {

        ModelAndView result;
        Collection<User> users;
        users = userService.findAll();
        Collection<User> res = new HashSet<>();
        res.addAll(userService.userNotModerator());

        users.removeAll(res);

        result = new ModelAndView("user/list");
        result.addObject("users", users);
        result.addObject("requestURI", "user/list.do");

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView result;

        User user = userService.create();

        result = createEditModelAndView2(user);

        return result;

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
    public ModelAndView register(@Valid User user, BindingResult binding) {
        ModelAndView result;

        if (! binding.hasErrors()) {
            result = createEditModelAndView2(user);
        } else {
            try {
                userService.registerAsUser(user);
                result = new ModelAndView("redirect:list.do");
            } catch (Throwable oops) {
                result = createEditModelAndView2(user, "general.commit.error");
            }
        }
        return result;
    }


    // Ancillary methods ------------------------------------------------


    @RequestMapping(value = "/editp", method = RequestMethod.GET)
    public ModelAndView editp() {
        ModelAndView result;
        User user;

        user = userService.findByPrincipal();
        Assert.notNull(user);

        result = createEditModelAndView(user);

        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int userId) {
        ModelAndView result;
        User user;

        user = userService.findOne(userId);
        Assert.notNull(user);
        result = createEditModelAndView(user);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid User user, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors()) {
            result = createEditModelAndView(user);
        } else {
            try {
                User user1 = userService.save(user);
                creditCardService.save(user1.getCreditCard());

                result = new ModelAndView("welcome/index");
            } catch (Throwable oops) {
                result = createEditModelAndView(user, "user.commit.error");
            }
        }
        return result;
    }

    @RequestMapping(value = "/ban", method = RequestMethod.GET)
    public ModelAndView banUser(int userId) {

        ModelAndView res;
        User user = userService.findOne(userId);


        userService.banUser(user);
        res = new ModelAndView("redirect:listAll.do");


        return res;


    }


    @RequestMapping(value = "/unban", method = RequestMethod.GET)
    public ModelAndView unbanUser(int userId) {

        ModelAndView res;
        User user = userService.findOne(userId);

        userService.unbanUser(user);
        res = new ModelAndView("redirect:listAll.do");


        return res;


    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(User user) {
        ModelAndView result;
        try {
            userService.delete(user);
            result = new ModelAndView("welcome/index");
        } catch (Throwable oops) {
            result = createEditModelAndView(user, "user.commit.error");
        }

        return result;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView lessorViewAn(@RequestParam int userId) {

        ModelAndView result;
        User user = userService.findOne(userId);


        result = new ModelAndView("user/view");
        result.addObject("name", user.getName());
        result.addObject("surname", user.getSurname());
        result.addObject("email", user.getEmail());
        result.addObject("phoneNumber", user.getPhoneNumber());
        result.addObject("questions", user.getQuestions());
        result.addObject("requestURI", "user/view.do");
        return result;
    }
}
