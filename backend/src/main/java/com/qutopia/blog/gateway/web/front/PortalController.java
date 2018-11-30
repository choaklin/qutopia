package com.qutopia.blog.gateway.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author choaklin
 * @date 2018.11.29
 */
@Controller
@RequestMapping(value = "portal")
public class PortalController {


    @GetMapping
    public String index() {

        return "index";
    }
}
