package com.biz.framework.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SystemWeb {

    @GetMapping("/")
    public String index(ModelAndView modelAndView) {
        return "index";
    }

    @GetMapping("/{dept1}")
    public ModelAndView view(@PathVariable("dept1") String dept1, ModelAndView modelAndView) {

        modelAndView.setViewName(dept1);
        return modelAndView;
    }

    @GetMapping("/{dept1}/{dept2}")
    public ModelAndView view(@PathVariable("dept1") String dept1, @PathVariable("dept2") String dept2, ModelAndView modelAndView) {
        modelAndView.addObject("path", "/" + dept1 + "/" + dept2);
        modelAndView.setViewName(dept1 + "/" + dept2);
        return modelAndView;
    }

    @GetMapping("/{dept1}/{dept2}/{dept3}")
    public ModelAndView view(@PathVariable("dept1") String dept1, @PathVariable("dept2") String dept2, @PathVariable("dept3") String dept3, ModelAndView modelAndView) {
        modelAndView.addObject("path", "/" + dept1 + "/" + dept2 + "/" + dept3);
        modelAndView.setViewName(dept1 + "/" + dept2 + "/" + dept3);
        return modelAndView;
    }

}
