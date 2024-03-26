package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    // @Autowired otra forma de hacerlo sin constructor. Lo busca solo con reflection
    private final UserService us;

    @Autowired // Este es el constructor q quiero por reflection
    public HelloWorldController(final UserService us) {
        this.us = us;
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/")
    @RequestMapping("/")
    public ModelAndView registerForm() {
       final ModelAndView mav = new ModelAndView("helloworld/registerForm");
       return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ModelAndView register(@RequestParam("username") String username) {
        final User user = us.create(username);
        return new ModelAndView("redirect:/" + user.getUserId());
    }

    @RequestMapping("/{userId:\\d+}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("helloworld/profile");
        mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{nonnumeric:[a-z]+}")
    public ModelAndView userProfile() {
        final ModelAndView mav = new ModelAndView("helloworld/profile");
        mav.addObject("user", us.findById(-1).orElseThrow(UserNotFoundException::new));
        return mav;
    }

}
