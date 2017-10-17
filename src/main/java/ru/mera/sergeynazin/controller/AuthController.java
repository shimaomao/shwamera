package ru.mera.sergeynazin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @RequestMapping(value = "/panel**", method = RequestMethod.GET)
    public ModelAndView panelPage(){

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Admin Panel");
        model.addObject("msg", "Role ADMIN only!");
        model.setViewName("panel");
        return model;

    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout){
        ModelAndView model = new ModelAndView();
        if (error != null){
            model.addObject("error", "Invalid username and pass!");
        }

        if (logout != null){
            model.addObject("msg", "Loout!!!");
        }

        model.setViewName("login");

        return model;
    }
}
