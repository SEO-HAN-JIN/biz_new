package com.biz.framework.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SystemWeb {

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("loadPage", "main/index/view");
        modelAndView.setViewName("layout/defaultlayout");
        return modelAndView;
    }

    @GetMapping("/{dept1}/{dept2}")
    public ModelAndView view(@PathVariable("dept1") String dept1, @PathVariable("dept2") String dept2, ModelAndView modelAndView) {

        modelAndView.setViewName(dept1 + "/" + dept2);
        return modelAndView;
    }

    @GetMapping("/index")
    public String index() {
        return "main/index";
    }

}
