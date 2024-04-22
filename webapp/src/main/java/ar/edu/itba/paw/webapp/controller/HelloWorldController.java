package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HelloWorldController {

    // @Autowired otra forma de hacerlo sin constructor. Lo busca solo con reflection
    private final UserService us;

    @Autowired // Este es el constructor q quiero por reflection
    public HelloWorldController(final UserService us) {
        this.us = us;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        final PawUserDetails user = (PawUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final long userId = us.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new).getUserId();
        return new ModelAndView("redirect:/" + userId);
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/")
    @RequestMapping("/create")
    public ModelAndView registerForm(@ModelAttribute("userform") UserForm form) {
        final ModelAndView mav = new ModelAndView("helloworld/registerForm");
        // No necesito mav.addObject("form", form), lo hace por default el ModelAttribute
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ModelAndView register(@Valid @ModelAttribute("userform") UserForm form, final BindingResult errors) {
        // if hasAnyError then showFormAgain + showErros
        if(errors.hasErrors() || !form.getPassword().equals(form.getRepeatPassword())) {
            return registerForm(form);
        }
        // else

        final User user = us.create(form.getUsername(), form.getPassword());
        return new ModelAndView("redirect:/" + user.getUserId());
    }

    @RequestMapping("/{userId:\\d+}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("helloworld/profile");
        mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

/*    @RequestMapping(method = RequestMethod.GET, path = "/{nonnumeric:[a-z]+}")
    public ModelAndView userProfile() {
        final ModelAndView mav = new ModelAndView("helloworld/profile");
        mav.addObject("user", us.findById(-1).orElseThrow(UserNotFoundException::new));
        return mav;
    }*/

    // Lo agrega a todos los metodos del controlador (ej. estado del carrito)
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return us.findById(
                1 // TODO: Obtain the current user id from the session
        ).orElse(null);
    }

    @RequestMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("helloworld/login");
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }
}
