package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.gateway.TemplateVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author choaklin
 * @date 2018.11.29
 */
@Controller
@RequestMapping(value = "portal")
public class PortalController {


    @GetMapping
    public String index(HttpServletRequest request, Model model) {

        // todo  最近5次游行的首张照片
        List<String> latestTravelThumbnails = Arrays.asList(
                "/public/images/tourist/hangzhou.jpg",
                "/public/images/tourist/huangshan.jpg",
                "/public/images/tourist/yunding.jpg",
                "/public/images/tourist/pingtan.jpg",
                "/public/images/tourist/chaka.jpg"
        );

        model.addAttribute(TemplateVariable.LATEST_TRAVEL_THUMBNAILS, latestTravelThumbnails);

        return "index";
    }
}
