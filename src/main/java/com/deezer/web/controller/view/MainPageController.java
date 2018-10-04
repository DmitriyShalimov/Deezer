package com.deezer.web.controller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping(path = "/**")
    public String loadMainPage() {
        logger.info("Requesting main page");
        return "index.html";
    }
}