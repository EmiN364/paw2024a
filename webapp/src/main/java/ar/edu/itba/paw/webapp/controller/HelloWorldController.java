package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.UserServiceImpl;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    // @Autowired otra forma de hacerlo sin constructor. Lo busca solo con reflection
    private final UserServiceImpl us;

    @Autowired // Este es el constructor q quiero por reflection
    public HelloWorldController(final UserService us) {
        this.us = (UserServiceImpl) us;
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/")
    @RequestMapping("/")
    public ModelAndView hello() {
       final ModelAndView mav = new ModelAndView("helloworld/hello");
       mav.addObject("user", us.findById(1));
       return mav;
    }

}
