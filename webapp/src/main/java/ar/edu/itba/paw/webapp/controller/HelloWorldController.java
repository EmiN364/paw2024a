package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView hello(@RequestParam(value = "userId", defaultValue = "1") final long userId) {
       final ModelAndView mav = new ModelAndView("helloworld/hello");
       mav.addObject("user", us.findById(userId));
       return mav;
    }

    @RequestMapping("/{userId:\\d+}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("helloworld/hello");
        mav.addObject("user", us.findById(userId));
        return mav;
    }

    @RequestMapping("/{nonnumeric:[a-z]+}")
    public ModelAndView userProfile() {
        final ModelAndView mav = new ModelAndView("helloworld/hello");
        mav.addObject("user", us.findById(-1));
        return mav;
    }

}
